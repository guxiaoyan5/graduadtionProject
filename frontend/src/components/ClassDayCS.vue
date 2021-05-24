<template>
  <div style="margin-left: 30px;">
    <el-row :gutter="20" style="height: 70px">
      <el-col :span="24" style="border-radius: 4px;">
        <el-card shadow="always" style="width: content-box;height: content-box;text-align: left"
                 body-style="padding: 10px 5px 0px 20px;height:60px">
          <el-form :inline="true" :model="formInline" style="margin-top: 5px;height: 40px" :rules="rules"
                   ref="formInline">
            <el-form-item label="学院" prop="id">
              <el-select
                v-model="formInline.id"
                style="width: 200px;"
                @change="changeMajor('college')"
                placeholder="请选择"
                clearable>
                <el-option
                  v-for="item in colleges"
                  :key="item.id"
                  :label="item.college"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专业" prop="id">
              <el-select
                v-model="formInline.majorId"
                style="width: 200px;"
                placeholder="请选择"
                clearable>
                <el-option
                  v-for="item in majors"
                  :key="item.id"
                  :label="item.major"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="day" style="margin-left: 20px">
              <div class="block">
                <span class="demonstration" style="margin-right: 5px">周</span>
                <el-date-picker
                  v-model="formInline.day"
                  type="week"
                  style="width: 150px;"
                  format="yyyy 第 WW 周"
                  placeholder="选择周">
                </el-date-picker>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit('formInline')">查询</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height: 550px">
      <el-col :span="24" style="height: 500px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart1" ref="chart1" style="height:100%;width: 100%"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height: 550px">
      <el-col :span="24" style="height: 500px;">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart2" ref="chart2" style="height: 100%;width: 100%;margin-left: 10px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
const echarts = require('echarts/lib/echarts');
require('echarts/lib/component/title');
require('echarts/lib/component/toolbox');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/grid');
require('echarts/lib/component/legend');
require('echarts/lib/chart/line');
require('echarts/lib/component/markLine');
require('echarts/lib/component/markPoint');
require('echarts/lib/chart/bar');
export default {
  name: "ClassDayCS",
  data() {
    let _this = this;
    axios.get('http://localhost:9090/schoolUser3/getColleges').then(function (response) {
      _this.colleges = response.data.data;
    }).catch(function (error) {
      console.log(error)
    })
    return {
      rules: {
        id: [{required: true, message: '请选择学院', trigger: 'change'}],
        day: [{required: true, message: '请选择日期', trigger: 'change'}],
        majorId: [{required: true, message: '请选择学院', trigger: 'change'}],
      },
      colleges: _this.colleges,
      formInline: {
        id: '',
        day: '',
        majorId:'',
      },
      majors:[],
      collegeData: [],
      chart1: '',
      chart2: '',
    }
  },
  mounted() {
    this.initChart1();
    this.initChart2();
  },
  methods: {
    onSubmit(formName) {
      let _this = this;
      this.chart1.showLoading()
      this.chart2.showLoading()
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:9090/schoolUser3/getClassDayCS', {
            id: _this.formInline.majorId,
            day: _this.formInline.day
          }).then(function (response) {
            _this.collegeData = response.data.data;
            let name = new Set();
            for (let i = 0; i < _this.collegeData.length; i++) {
              name.add(_this.collegeData[i].name);
            }
            name = Array.from(name);
            let date = new Date(_this.formInline.day);
            let dates = [];
            for (let i = -1; i < 6; i++) {
              let newDate = new Date(+date + 1000 * 60 * 60 * 24 * i);
              let month = newDate.getMonth() + 1 < 10 ? '0' + (newDate.getMonth() + 1) : (newDate.getMonth() + 1) + '';
              dates.push(newDate.getFullYear() + "-" + month + "-" + newDate.getDate())
            }
            let data = Array.from({length: name.length}).map(item => (
              Array.from({length: 7}).map(item => (0))
            ))
            let high = Array.from({length: name.length}).map(item => (
              Array.from({length: 7}).map(item => (0))
            ))
            let low = Array.from({length: name.length}).map(item => (
              Array.from({length: 7}).map(item => (0))
            ))
            for (let k = 0; k < _this.collegeData.length; k++) {
              for (let i = 0; i < 7; i++) {
                for (let j = 0; j < name.length; j++) {
                  if (_this.collegeData[k].name == name[j] && _this.collegeData[k].day == dates[i]) {
                    data[j][i] = Math.round(_this.collegeData[k].consumption_average_money * 100) / 100;
                    high[j][i] = _this.collegeData[k].high;
                    low[j][i] = _this.collegeData[k].low;
                  }
                }
              }
            }
            let series = [];
            for (let i = 0; i < name.length; i++) {
              let temp = {
                name: name[i],
                type: 'line',
                data: data[i],
                markPoint: {
                  data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                  ]
                },
                markLine: {
                  data: [
                    {type: 'average', name: '平均值'}
                  ]
                }
              }
              series.push(temp)
            }
            _this.chart1.setOption({
              xAxis: {
                data: dates
              },
              legend: {
                data: name
              },
              series: series
            })
            let series1 = [];
            let name1 = [];
            for (let i = 0; i < name.length; i++) {
              name1.push(name[i] + '高于人均消费')
              let temp = {
                name: name[i] + '高于人均消费',
                type: 'bar',
                data: high[i],
                markPoint: {
                  data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                  ]
                },
                markLine: {
                  data: [
                    {type: 'average', name: '平均值'}
                  ]
                }
              };
              series1.push(temp);
            }
            for (let i = 0; i < name.length; i++) {
              name1.push(name[i] + '低于人均消费')
              let temp = {
                name: name[i] + '低于人均消费',
                type: 'bar',
                data: low[i],
                markPoint: {
                  data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                  ]
                },
                markLine: {
                  data: [
                    {type: 'average', name: '平均值'}
                  ]
                }
              };
              series1.push(temp);
            }
            _this.chart2.setOption({
              legend: {
                data: name1
              },
              xAxis: [
                {
                  data: dates
                }
              ],
              series: series1
            })
            _this.chart1.hideLoading()
            _this.chart2.hideLoading()
          }).catch(function (error) {
            console.log(error)
          })
        } else {
          console.log('error submit!!');
          return false;
        }

      })
    },
    initChart1() {
      this.chart1 = echarts.init(document.getElementById("chart1"));
      let option = {
        title: {
          text: '每日人均消费',
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        toolbox: {
          show: true,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value} 元'
          }
        },
        series: []
      };
      this.chart1.setOption(option);
    },
    initChart2() {
      this.chart2 = echarts.init(document.getElementById("chart2"));
      let option = {
        title: {
          text: '消费次数统计',
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        toolbox: {
          show: true,
          feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        calculable: true,
        xAxis: [
          {
            type: 'category',
            data: []
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: []
      };
      this.chart2.setOption(option)
    },
    changeMajor(college){
      let _this = this
      axios.post('http://localhost:9090/schoolUser3/getMajors',{
        id:_this.formInline.id
      }).then(function (response){
        _this.majors = response.data.data;
      }).catch(function (error){
        console.log(error)
      });
    }
  }
}
</script>

<style scoped>

</style>
