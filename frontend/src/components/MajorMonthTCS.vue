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
                clearable>
                <el-option
                  v-for="item in colleges"
                  :key="item.id"
                  :label="item.college"
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
          <span style="margin-right: 5px">专业</span>
          <el-select
            v-model="college"
            style="width: 200px;"
            placeholder="请选择"
            @change="collegeChange('college')"
            clearable>
            <el-option
              v-for="item in collegeName"
              :key="item.id"
              :label="item.college"
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
require('echarts/lib/component/timeline');
require('echarts/lib/component/title');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/grid');
require('echarts/lib/component/legend');
require('echarts/lib/chart/bar');
require('echarts/lib/chart/pie');
require('echarts/lib/component/toolbox');
require('echarts/lib/chart/bar');
require('echarts/lib/component/markLine');
require('echarts/lib/component/markPoint');
export default {
  name: "MajorMonthTCS",
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
        year: [{required: true, message: '请选择日期', trigger: 'change'}]
      },
      college: '',
      colleges: _this.colleges,
      formInline: {
        id: '',
        year: ''
      },
      collegeData: [],
      chart2: '',
      chart1: '',
      collegeName: [],
    }
  },
  mounted() {
    this.initChart1();
    this.initChart2();
  },
  methods: {
    onSubmit(formName) {
      this.college = ''
      let _this = this;
      this.chart1.showLoading()
      this.chart2.showLoading()
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:9090/schoolUser3/getMajorMonthTCS', {
            id: _this.formInline.id,
            year: _this.formInline.year
          }).then(function (response) {
            _this.collegeData = response.data.data;
            _this.collegeName = [];
            // for (let i = 0; i < _this.colleges.length; i++) {
            //   for (let j = 0; j < _this.formInline.id.length; j++) {
            //     if (_this.colleges[i].id === _this.formInline.id[j]) {
            //       _this.collegeName.push(_this.colleges[i]);
            //     }
            //   }
            // }
            let name = new Set();
            for (let i = 0; i < _this.collegeData.length; i++) {
              if(!name.has(_this.collegeData[i].name)){
                _this.collegeName.push({
                  id:_this.collegeData[i].id,
                  college:_this.collegeData[i].name
                });
              }
              name.add(_this.collegeData[i].name);
            }
            name = Array.from(name);
            let data = Array.from({length: 12}).map(item => (
              Array.from({length: 3}).map(item1 =>
                Array.from({length: name.length}).map(item3 => (0))
              )
            ));
            for (let k = 0; k < _this.collegeData.length; k++) {
              for (let i = 0; i < 12; i++) {
                for (let j = 0; j < name.length; j++) {
                  if (_this.collegeData[k].name == name[j] && _this.collegeData[k].month == i) {
                    if (_this.collegeData[k].consumption_category == '早') {
                      data[i][0][j] = Math.round(_this.collegeData[k].consumption_average_money * 100) / 100
                    } else if (_this.collegeData[k].consumption_category == '午') {
                      data[i][1][j] = Math.round(_this.collegeData[k].consumption_average_money * 100) / 100
                    } else {
                      data[i][2][j] = Math.round(_this.collegeData[k].consumption_average_money * 100) / 100
                    }
                  }
                }
              }
            }
            let options = [];
            for (let i = 0; i < 12; i++) {
              options.push({
                title: {text: (i+1) + '月人均三餐消费'},
                series: [
                  {data: data[i][0]},
                  {data: data[i][1]},
                  {data: data[i][2]},
                ]
              })
            }
            _this.chart1.setOption({
              baseOption: {
                xAxis: [{
                  'data': name,
                }],
              },
              options: options
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

      });
    },
    initChart1() {
      this.chart1 = echarts.init(document.getElementById("chart1"))
      let option = {
        baseOption: {
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
          timeline: {
            axisType: 'category',
            // realtime: false,
            // loop: false,
            autoPlay: true,
            // currentIndex: 2,
            playInterval: 1000,
            // controlStyle: {
            //     position: 'left'
            // },
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            label: {
              formatter: function (s) {
                return s;
              }
            }
          },
          title: {
            subtext: '三餐数据分析'
          },
          tooltip: {},
          legend: {
            left: 'right',
            data: ['早餐', '午餐', '晚餐'],
          },
          calculable: true,
          grid: {
            top: 80,
            bottom: 100,
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow',
                label: {
                  show: true,
                  formatter: function (params) {
                    return params.value.replace('\n', '');
                  }
                }
              }
            }
          },
          xAxis: [
            {
              'type': 'category',
              'axisLabel': {'interval': 0},
              'data': [],
              splitLine: {show: false}
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '（元）'
            }
          ],
          series: [
            {name: '早餐', type: 'bar'},
            {name: '午餐', type: 'bar'},
            {name: '晚餐', type: 'bar'},
          ]
        },
        options: []
      };
      this.chart1.setOption(option)
    },
    initChart2() {
      this.chart2 = echarts.init(document.getElementById("chart2"))
      let option = {
        title: {
          text: '三餐消费次数',
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['早餐高于人均消费次数', '早餐低于人均消费次数', '午餐高于人均消费次数', '午餐低于人均消费次数', '晚餐高于人均消费次数', '晚餐低于人均消费次数']
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
            name: '早餐高于人均消费次数',
            type: 'bar',
            data: [],
          },
          {
            name: '早餐低于人均消费次数',
            type: 'bar',
            data: [],
          },
          {
            name: '午餐高于人均消费次数',
            type: 'bar',
            data: [],
          },
          {
            name: '午餐低于人均消费次数',
            type: 'bar',
            data: [],
          },
          {
            name: '晚餐高于人均消费次数',
            type: 'bar',
            data: [],
          },
          {
            name: '晚餐低于人均消费次数',
            type: 'bar',
            data: [],
          }
        ]
      };
      this.chart2.setOption(option)
    },
    collegeChange(form) {
      let blow = Array.from({length: 12}).map(item => (0));
      let bhigh = Array.from({length: 12}).map(item => (0));
      let llow = Array.from({length: 12}).map(item => (0));
      let lhigh = Array.from({length: 12}).map(item => (0));
      let dlow = Array.from({length: 12}).map(item => (0));
      let dhigh = Array.from({length: 12}).map(item => (0));
      for (let k = 0; k < this.collegeData.length; k++) {
        if (this.collegeData[k].id == this.college) {
          if (this.collegeData[k].consumption_category == '早') {
            blow[parseInt(this.collegeData[k].month) - 1] = this.collegeData[k].low
            bhigh[parseInt(this.collegeData[k].month) - 1] = this.collegeData[k].high
          } else if (this.collegeData[k].consumption_category == '午') {
            llow[parseInt(this.collegeData[k].month) - 1] = this.collegeData[k].low
            lhigh[parseInt(this.collegeData[k].month) - 1] = this.collegeData[k].high
          } else {
            dlow[parseInt(this.collegeData[k].month) - 1] = this.collegeData[k].low
            dhigh[parseInt(this.collegeData[k].month) - 1] = this.collegeData[k].high
          }
        }
      }
      this.chart2.setOption({
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
        series: [
          {
            data: bhigh,
          }, {
            data: blow,
          },
          {
            data: lhigh,
          }, {
            data: llow,
          }, {
            data: dhigh,
          }, {
            data: dlow,
          }
        ]
      })

    }
  }
}
</script>

<style scoped>

</style>
