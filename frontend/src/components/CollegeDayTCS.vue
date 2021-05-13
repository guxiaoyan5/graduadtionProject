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
require('echarts/lib/component/dataset');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/grid');
require('echarts/lib/component/legend');
require('echarts/lib/chart/bar');
require('echarts/lib/chart/pie');
require('echarts/lib/component/title');
require('echarts/lib/component/toolbox');
require('echarts/lib/chart/line');
require('echarts/lib/component/markLine');
require('echarts/lib/component/markPoint');
export default {
  name: "CollegeDayTCS",
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
        day: [{required: true, message: '请选择日期', trigger: 'change'}]
      },
      colleges: _this.colleges,
      formInline: {
        id: '',
        day: ''
      },
      collegeData: [],
      chart1: '',
      chart2: '',
    }
  },
  mounted() {
    this.initChart2();
    this.initChart1();
  },
  methods: {
    onSubmit(formName) {
      let _this = this
      this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.post('http://localhost:9090/schoolUser3/getCollegeDayTCS', {
              id: _this.formInline.id,
              day: _this.formInline.day
            }).then(function (response) {
              _this.collegeData = response.data.data;
            }).catch(function (error) {
              console.log(error)
            })
          } else {
            console.log('error submit!!');
            return false;
          }
          let date = new Date(this.formInline.day);
          let dates = [];
          for (let i = -1; i < 6; i++) {
            let newDate = new Date(+date + 1000 * 60 * 60 * 24 * i);
            let month = newDate.getMonth() + 1 < 10 ? '0' + (newDate.getMonth() + 1) : (newDate.getMonth() + 1) + '';
            dates.push(newDate.getFullYear() + "-" + month + "-" + newDate.getDate())
          }
          let b = ['早餐', 0, 0, 0, 0, 0, 0, 0]
          let l = ['午餐', 0, 0, 0, 0, 0, 0, 0]
          let d = ['晚餐', 0, 0, 0, 0, 0, 0, 0]
          let p = ['product']
          for (let i = 0; i < dates.length; i++) {
            p.push(dates[i])
          }
          for (let i = 0; i < _this.collegeData.length; i++) {
            for (let j = 0; j < dates.length; j++) {
              if (_this.collegeData[i].day == dates[j]) {
                if (_this.collegeData[i].consumption_category == '早') {
                  b[j + 1] = Math.round(_this.collegeData[i].consumption_average_money * 100) / 100
                } else if (_this.collegeData[i].consumption_category == '午') {
                  l[j + 1] = Math.round(_this.collegeData[i].consumption_average_money * 100) / 100
                } else {
                  d[j + 1] = Math.round(_this.collegeData[i].consumption_average_money * 100) / 100
                }
              }
            }
          }
          _this.chart1.setOption({
            dataset: {
              source: [
                p, b, l, d
              ]
            },
            series: [{type: 'bar', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
              {type: 'bar', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
              {type: 'bar', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
              {
                type: 'pie',
                id: 'pie',
                radius: '30%',
                center: ['50%', '25%'],
                emphasis: {focus: 'data'},
                label: {
                  formatter: '{b}: {@} ({d}%)'
                },
                encode: {
                  itemName: 'product',
                  value: '',
                  tooltip: ''
                }
              }]
          })

          let blow = Array.from({length: 12}).map(item => (0));
          let bhigh = Array.from({length: 12}).map(item => (0));
          let llow = Array.from({length: 12}).map(item => (0));
          let lhigh = Array.from({length: 12}).map(item => (0));
          let dlow = Array.from({length: 12}).map(item => (0));
          let dhigh = Array.from({length: 12}).map(item => (0));
          for (let k = 0; k < _this.collegeData.length; k++) {
            for (let i = 0; i < dates.length; i++) {
              if (dates[i] == _this.collegeData[k].day) {
                if (_this.collegeData[k].consumption_category == '早') {
                  blow[i] = _this.collegeData[k].low
                  bhigh[i] = _this.collegeData[k].high
                } else if (_this.collegeData[k].consumption_category == '午') {
                  llow[i] = _this.collegeData[k].low
                  lhigh[i] = _this.collegeData[k].high
                } else {
                  dlow[i] = _this.collegeData[k].low
                  dhigh[i] = _this.collegeData[k].high
                }
              }
            }
          }
          _this.chart2.setOption({
            xAxis: [
              {
                data: dates
              }
            ],
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
      );
    },
    initChart1() {
      this.chart1 = echarts.init(document.getElementById('chart1'));
      let option = {
        legend: {},
        tooltip: {
          trigger: 'axis',
          showContent: false
        },
        dataset: {
          source: []
        },
        xAxis: {type: 'category'},
        yAxis: {gridIndex: 0},
        grid: {top: '45%'},
        series: []
      };
      let _this = this
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
    },
    initChart2() {
      this.chart2 = echarts.init(document.getElementById('chart2'));
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
            data: []
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
      this.chart2.setOption(option);
    }
  }
}
</script>

<style scoped>

</style>
