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
                placeholder="请选择"
                @change="changeMajor()"
                clearable>
                <el-option
                  v-for="item in colleges"
                  :key="item.id"
                  :label="item.college"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专业" prop="majorId">
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
            <el-form-item prop="year" style="margin-left: 20px">
              <div class="block">
                <span class="demonstration" style="margin-right: 5px">年</span>
                <el-date-picker
                  v-model="formInline.year"
                  unlink-panels
                  style="width: 150px;"
                  type="year"
                  placeholder="选择年">
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
    <el-row :gutter="20" style="height: 70px">
      <el-col :span="24" style="border-radius: 4px;">
        <el-card shadow="always" style="width: content-box;height: content-box;text-align: left"
                 body-style="padding: 10px 5px 0px 20px;height:60px">
          <span style="margin-right: 5px">班级</span>
          <el-select
            v-model="college"
            style="width: 200px;"
            placeholder="请选择"
            @change="collegeChange('college')"
            clearable>
            <el-option
              v-for="item in collegeName"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
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
require('echarts/lib/chart/bar');
require('echarts/lib/component/markLine');
require('echarts/lib/component/markPoint');
require('echarts/lib/component/dataset');
require('echarts/lib/chart/line');
require('echarts/lib/chart/pie');
export default {
  name: "ClassMonthCS",
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
        majorId: [{required: true, message: '请选择学院', trigger: 'change'}],
        year: [{required: true, message: '请选择日期', trigger: 'change'}]
      },
      colleges: _this.colleges,
      college: '',
      majors:[],
      collegeName: [],
      formInline: {
        id: '',
        majorId:'',
        year: ''
      },
      collegeData: [],
      chart2: '',
      chart1: '',
    }
  },
  mounted() {
    this.initChart1();
    this.initChart2();
  },
  methods: {
    onSubmit(form) {
      let _this = this;
      this.$refs[form].validate((valid) => {
        this.college = ''
        if (valid) {
          axios.post('http://localhost:9090/schoolUser3/getClassMonthCS', {
            id: _this.formInline.majorId,
            year: _this.formInline.year
          }).then(function (response) {
            _this.collegeData = response.data.data;
          }).catch(function (error) {
            console.log(error)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
        _this.collegeName = [];
        let source = [
          ["product", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]
        ];
        let name = new Set();
        // this.college = this.collegeName[0].id
        for (let i = 0; i < _this.collegeData.length; i++) {
          if (name.has(_this.collegeData[i].name)) {
            for (let j = 0; j < source.length; j++) {
              if (source[j][0] == _this.collegeData[i].name) {
                source[j][parseInt(_this.collegeData[i].month)] = Math.round(_this.collegeData[i].consumption_average_money * 100) / 100;
              }
            }
          } else {
            name.add(_this.collegeData[i].name);
            _this.collegeName.push({
              id: _this.collegeData[i].id,
              name: _this.collegeData[i].name
            });
            let temp = [_this.collegeData[i].name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            temp[parseInt(_this.collegeData[i].month)] = Math.round(_this.collegeData[i].consumption_average_money * 100) / 100;
            source.push(temp)
          }
        }
        let series = [];
        for (let i = 0; i < source.length - 1; i++) {
          series.push({
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          })
        }
        series.push({
          type: 'pie',
          id: 'pie',
          radius: '30%',
          center: ['50%', '25%'],
          emphasis: {focus: 'data'},
          label: {
            formatter: '{b}: {@1} ({d}%)'
          },
          encode: {
            itemName: 'product',
            value: '1',
            tooltip: '1'
          }
        });
        _this.chart1.setOption({
          dataset: {
            source: source
          },
          series: series
        })
      });

    },
    initChart1() {
      this.chart1 = echarts.init(document.getElementById("chart1"));
      let option = {
        color: [
          '#5470c6',
          '#91cc75',
          '#fac858',
          '#ee6666',
          '#73c0de',
          '#3ba272',
          '#fc8452',
          '#9a60b4',
          '#ea7ccc',
        ],
        title: {
          text: '人均消费',
        },
        legend: {},
        tooltip: {
          trigger: 'axis',
          showContent: true
        },
        dataset: {
          source: []
        },
        xAxis: {type: 'category'},
        yAxis: {gridIndex: 0, name: '人均消费（元）'},
        grid: {top: '45%'},
        series: []
      };
      let _this = this;
      this.chart1.on('updateAxisPointer', function (event) {
        let xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          let dimension = xAxisInfo.value + 1;
          _this.chart1.setOption({
            series: {
              id: 'pie',
              label: {
                formatter: '{b}: {@[' + dimension + ']} 元({d}%)'
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });
      this.chart1.setOption(option);
      this.chart1.hideLoading();
    },
    initChart2() {
      this.chart2 = echarts.init(document.getElementById("chart2"));
      let option = {
        color: [
          '#5470c6',
          '#91cc75',
          '#fac858',
          '#ee6666',
          '#73c0de',
          '#3ba272',
          '#fc8452',
          '#9a60b4',
          '#ea7ccc',
        ],
        title: {
          text: '消费次数统计',
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['高于人均次数', '低于人均次数']
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
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '高于人均次数',
            type: 'bar',
            data: [],
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
          },
          {
            name: '低于人均次数',
            type: 'bar',
            data: [],
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
        ]
      };
      this.chart2.setOption(option);
      this.chart2.hideLoading()
    },
    changeMajor(){
      let _this = this
      axios.post('http://localhost:9090/schoolUser3/getMajors',{
        id:_this.formInline.id
      }).then(function (response) {
        _this.majors = response.data.data;
      }).catch(function (error) {
        console.log(error)
      })
    },
    collegeChange(college) {
      let high = Array.from({length: 12}).map(item => (0));
      let low = Array.from({length: 12}).map(item => (0));
      for (let i = 0; i < this.collegeData.length; i++) {
        if (this.collegeData[i].id == this.college) {
          let month = this.collegeData[i].month
          high[parseInt(month) - 1] = this.collegeData[i].high;
          low[parseInt(month) - 1] = this.collegeData[i].low;
        }
      }
      this.chart2.setOption({
        series: [
          {
            data: high,
          },
          {
            data: low,
          }
        ]
      })
    }
  }
}
</script>

<style scoped>

</style>
