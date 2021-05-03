<template>
  <div style="margin-left: 30px">
    <el-row :gutter="20" style="height: 70px">
      <el-col :span="24" style="border-radius: 4px;">
        <el-card shadow="always" style="width: content-box;height: content-box;text-align: left"
                 body-style="padding: 10px 5px 0px 20px;height:60px">
          <el-form :inline="true" :model="formInline" style="margin-top: 5px;height: 40px" :rules="rules"
                   ref="formInline">
            <el-form-item label="级别" prop="id">
              <el-cascader
                style="width: 300px"
                placeholder="请选择学院或专业或班级"
                :props="props" rmI
                :model="formInline.id"
                collapse-tags
                ref="id"
                clearable></el-cascader>
            </el-form-item>
            <el-form-item prop="choice">
              <el-switch
                v-model="formInline.choice"
                active-text="按月"
                inactive-text="按日">
              </el-switch>
            </el-form-item>
            <el-form-item label="月份" v-if="formInline.choice" prop="date">
              <el-date-picker
                type="monthrange"
                unlink-panels
                style="width: 300px"
                v-model="formInline.date"
                range-separator="至"
                start-placeholder="开始月份"
                end-placeholder="结束月份"
                :picker-options="MonthPickerOptions">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="日期" v-if="formInline.choice===false" prop="date">
              <el-date-picker
                type="daterange"
                unlink-panels
                style="width: 300px"
                v-model="formInline.date"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="DayPickerOptions">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit('formInline')">查询</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height: 440px">
      <el-col :span="13" style="height: 400px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart1" ref="chart1" style="height:100%;width: 100%"></div>
        </el-card>
      </el-col>
      <el-col :span="10" style="height: 400px;">
        <el-card style="height:100%;width: 100%;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart2" ref="chart2" style="height:100%;width: 130%;"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height:500px">
      <el-col :span="24" style="height: 450px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:90%;width: 100%;padding:0px">
          <div slot="header" class="clearfix">
            <span>人均消费统计图</span>
            <el-select v-model="nameData" filterable placeholder="请选择" @change="fetchData3" style="float: right;"size="mini">
              <el-option
                v-for="item in nameDataS"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </div>
          <div id="chart3" ref="chart3" style="height:100%;width: 100%;"></div>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script>
import axios from "axios";

const echarts = require('echarts/lib/echarts');
require('echarts/lib/component/title');
require('echarts/lib/component/legend');
require('echarts/lib/chart/radar');
require('echarts/lib/component/timeline');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/grid');
require('echarts/lib/chart/bar');
require('echarts/lib/chart/pie');
export default {
  name: "ThreeMeals",
  data() {
    return {
      rules: {
        // id: [{required: true, message: '请选择级别', trigger: 'change'}],
        date: [{
          type: 'array', required: true, message: '请选择日期', trigger: 'change'
        }]
      },
      formInline: {
        id: '',
        choice: true,
        date: Array,
      },
      props: {
        multiple: true,
        checkStrictly: true,
        lazy: true,
        lazyLoad(node, resolve) {
          const {level} = node;
          let nodes;
          setTimeout(() => {
            axios.post("http://localhost:9090/schoolUser/getData", {
              level: level,
              value: level !== 0 ? node.data.value : null,
            }).then(function (response) {
              let data = response.data;
              if (data.code === 1) {
                nodes = data.data;
                resolve(nodes);
              } else {
                console.log();
              }
            }).catch(function (error) {
              console.log(error);
            });
          }, 1000);
        },
      },
      MonthPickerOptions: {
        shortcuts: [{
          text: '本月',
          onClick(picker) {
            picker.$emit('pick', [new Date(), new Date()]);
          }
        }, {
          text: '今年至今',
          onClick(picker) {
            const end = new Date();
            const start = new Date(new Date().getFullYear(), 0);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      DayPickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      Data: [],
      echarts1: '',
      echarts2: '',
      echarts3: '',
      echarts4: '',
      echartsData3: [],
      nameData: '',
      nameDataS: [],
      dataResult3:[],
    };
  },
  mounted() {
    this.initChart1();
    this.initChart2();
    this.initChart3();
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let _this = this;
          const nodes = _this.$refs['id'].getCheckedNodes();
          let obj = [];
          nodes.forEach(node => {
            obj.push({
              value: node.value,
              label: node.label,
              level: node.level,
            })
          });
          axios.post('http://localhost:9090/schoolUser/getThreeMeals', {
            id: obj,
            choice: _this.formInline.choice,
            date: _this.formInline.date
          }).then(function (response) {
            _this.Data = response.data.data;
          }).catch(function (error) {
            console.log('error!!');
            console.log(error)
          })
          this.fetchData();
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    initChart1() {
      this.echarts1 = echarts.init(document.getElementById('chart1'));
      let option = {
        baseOption: {
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
            data: [],
            label: {
              formatter: function (s) {
                return (new Date(s)).getFullYear();
              }
            }
          },
          title: {
            subtext: '平均每人次消费统计图'
          },
          tooltip: {},
          legend: {
            left: 'right',
            data: ['早餐', '午餐', '晚餐'],
            // selected: {
            //   'GDP': false, '金融': false, '房地产': false
            // }
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
          xAxis: [{
            'type': 'category',
            'axisLabel': {'interval': 0},
            'data': [],
            splitLine: {show: false}
          }],
          yAxis: [{
            type: 'value',
            name: '消费（元）'
          }],
          series: [
            {name: '早餐', type: 'bar'},
            {name: '午餐', type: 'bar'},
            {name: '晚餐', type: 'bar'},
            // {
            //   name: '消费占比',
            //   type: 'pie',
            //   center: ['75%', '35%'],
            //   radius: '28%',
            //   z: 100
            // }
          ]
        },
        options: [],
      };
      this.echarts1.setOption(option);
      this.echarts1.hideLoading();
    },
    initChart2() {
      this.echarts2 = echarts.init(document.getElementById('chart2'));
      let option = {
        color: ['#67F9D8', '#FFE434', '#56A3F1', '#FF917C'],
        title: {
          text: '三餐消费总和'
        },
        legend: {
          data: []
        },
        radar:
          {
            indicator: [
              {text: '早餐'},
              {text: '午餐'},
              {text: '晚餐'},
            ],
            center: ['25%', '50%'],
            radius: 120,
            startAngle: 90,
            splitNumber: 4,
            shape: 'circle',
            name: {
              formatter: '【{value}】',
              textStyle: {
                color: '#428BD4'
              }
            },
            splitArea: {
              areaStyle: {
                color: ['#77EADF', '#26C3BE', '#64AFE9', '#428BD4'],
                shadowColor: 'rgba(0, 0, 0, 0.2)',
                shadowBlur: 10
              }
            },
            axisLine: {
              lineStyle: {
                color: 'rgba(211, 253, 250, 0.8)'
              }
            },
            splitLine: {
              lineStyle: {
                color: 'rgba(211, 253, 250, 0.8)'
              }
            }
          },
        series: []
      };
      this.echarts2.setOption(option);
      this.echarts2.hideLoading();
    },
    initChart3() {
      this.echarts3 = echarts.init(document.getElementById('chart3'));
      let option = {
        legend: {},
        tooltip: {
          trigger: 'axis',
          showContent: true
        },
        dataset: {
          source: [
            // ['product', '2015', '2016', '2017', '2018', '2019', '2020'],
            // ['Milk Tea', 56.5, 82.1, 88.7, 70.1, 53.4, 85.1],
            // ['Matcha Latte', 51.1, 51.4, 55.1, 53.3, 73.8, 68.7],
            // ['Cheese Cocoa', 40.1, 62.2, 69.5, 36.4, 45.2, 32.5],
          ]
        },
        xAxis: {type: 'category'},
        yAxis: {gridIndex: 0},
        grid: {left: '45%'},
        series: [
          {type: 'line', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
          {type: 'line', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
          {type: 'line', smooth: true, seriesLayoutBy: 'row', emphasis: {focus: 'series'}},
          {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['25%', '50%'],
            emphasis: {focus: 'data'},
            // label: {
            //     formatter: '{b}: {@2012} ({d}%)'
            // },
            // encode: {
            //     itemName: 'product',
            //     value: '2012',
            //     tooltip: '2012'
            // }
          }
        ]
      };
      let _this = this;
      this.echarts3.on('updateAxisPointer', function (event) {
        let xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          let dimension = xAxisInfo.value + 1;
          _this.echarts3.setOption({
            series: {
              id: 'pie',
              label: {
                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });
      this.echarts3.setOption(option);
      this.echarts3.hideLoading();
    },
    fetchData() {
      this.echarts1.showLoading();
      this.echarts2.showLoading();
      let name = [];
      this.Data.forEach(item => (
        name.push(item.name)
      ));
      this.nameDataS = name;
      let date = new Set();
      if (this.formInline.choice) {
        this.Data.forEach(item => (
          item.consumeThreeMonthData.forEach(item1 => (
            date.add(item1.year + "-" + item1.month)
          ))
        ));
      } else {
        this.Data.forEach(item => (
          item.consumeThreeDayData.forEach(item1 => (
            date.add(item1.date + "")
          ))
        ));
      }
      date = Array.from(date).sort();
      let dataResult2 = [];
      let dataResult1 = [];
      let dataResult3 = Array.from({length: name.length});
      let dataTempB = Array.from({length: date.length})
      let dataTempL = Array.from({length: date.length})
      let dataTempD = Array.from({length: date.length})
      for (let i = 0; i < date.length; i++) {
        dataTempB[i] = {value: Array.from({length: name.length}).map(item => (0)), time: date[i]}
        dataTempL[i] = {value: Array.from({length: name.length}).map(item => (0)), time: date[i]}
        dataTempD[i] = {value: Array.from({length: name.length}).map(item => (0)), time: date[i]}
        dataResult3[i] = {
          name: name[i],
          source: [
            Array.from({length: date.length + 1}),
            Array.from({length: date.length + 1}).map(item => (0)),
            Array.from({length: date.length + 1}).map(item => (0)),
            Array.from({length: date.length + 1}).map(item => (0)),
          ]
        }
        for (let j = 0; j < date.length; j++) {
          dataResult3[i].source[0][j+1] = date[j];
        }
        dataResult3[i].source[0][0] = "product";
        dataResult3[i].source[1][0] = "早餐";
        dataResult3[i].source[2][0] = "午餐";
        dataResult3[i].source[3][0] = "晚餐";
      }
      for (let i = 0; i < this.Data.length; i++) {
        let temp2 = {
          value: [],
          name: this.Data[i].name,
          label: {
            show: true,
            formatter: function (params) {
              return params.value;
            }
          }
        };
        let breakfast = 0, lunch = 0, dinner = 0;
        if (this.formInline.choice) {
          for (let j = 0; j < this.Data[i].consumeThreeMonthData.length; j++) {
            let l = this.Data[i].consumeThreeMonthData[j].consumption_category
            if (l === '早') {
              breakfast += this.Data[i].consumeThreeMonthData[j].consumption_total_money;
            } else if (l === '午') {
              lunch += this.Data[i].consumeThreeMonthData[j].consumption_total_money;
            } else {
              dinner += this.Data[i].consumeThreeMonthData[j].consumption_total_money;
            }
            for (let k = 0; k < date.length; k++) {
              let timeTemp = this.Data[i].consumeThreeMonthData[j].year + '-' + this.Data[i].consumeThreeMonthData[j].month
              if (timeTemp === dataTempB[k].time && l === '早') {
                dataTempB[k].value[i] = this.Data[i].consumeThreeMonthData[j].consumption_average_money
              }
              if (timeTemp === dataTempL[k].time && l === '午') {
                dataTempL[k].value[i] = this.Data[i].consumeThreeMonthData[j].consumption_average_money
              }
              if (timeTemp === dataTempD[k].time && l === '晚') {
                dataTempD[k].value[i] = this.Data[i].consumeThreeMonthData[j].consumption_average_money
              }
              if (timeTemp === dataResult3[i].source[0][k+1] && l === '早') {
                dataResult3[i].source[1][k+1] = this.Data[i].consumeThreeMonthData[j].consumption_student_average_money
              }
              if (timeTemp === dataResult3[i].source[0][k+1] && l === '午') {
                dataResult3[i].source[2][k+1] = this.Data[i].consumeThreeMonthData[j].consumption_student_average_money
              }
              if (timeTemp === dataResult3[i].source[0][k+1] && l === '晚') {
                dataResult3[i].source[3][k+1] = this.Data[i].consumeThreeMonthData[j].consumption_student_average_money
              }
            }
          }
        } else {
          for (let j = 0; j < this.Data[i].consumeThreeDayData.length; j++) {
            let l = this.Data[i].consumeThreeDayData[j].consumption_category
            if (l === '早') {
              breakfast += this.Data[i].consumeThreeDayData[j].consumption_total_money;
            } else if (l === '午') {
              lunch += this.Data[i].consumeThreeDayData[j].consumption_total_money;
            } else {
              dinner += this.Data[i].consumeThreeDayData[j].consumption_total_money;
            }
            for (let k = 0; k < date.length; k++) {
              let timeTemp = this.Data[i].consumeThreeDayData[j].year + '-' + this.Data[i].consumeThreeDayData[j].month
              if (timeTemp === dataTempB[k].time && l === '早') {
                dataTempB[k].value[i] = this.Data[i].consumeThreeDayData[j].consumption_average_money
              }
              if (timeTemp === dataTempL[k].time && l === '午') {
                dataTempL[k].value[i] = this.Data[i].consumeThreeDayData[j].consumption_average_money
              }
              if (timeTemp === dataTempD[k].time && l === '晚') {
                dataTempD[k].value[i] = this.Data[i].consumeThreeDayData[j].consumption_average_money
              }
              if (timeTemp === dataResult3[i].source[0][k+1] && l === '早') {
                dataResult3[i].source[1][k+1] = this.Data[i].consumeThreeDayData[j].consumption_student_average_money
              }
              if (timeTemp === dataResult3[i].source[0][k+1] && l === '午') {
                dataResult3[i].source[2][k+1] = this.Data[i].consumeThreeDayData[j].consumption_student_average_money
              }
              if (timeTemp === dataResult3[i].source[0][k+1] && l === '晚') {
                dataResult3[i].source[3][k+1] = this.Data[i].consumeThreeDayData[j].consumption_student_average_money
              }
            }

          }
        }
        temp2.value.push(breakfast, lunch, dinner);
        dataResult2.push(temp2)
      }

      function dataFormatter(obj, nameData) {
        let temp;
        let result = []
        for (let i = 0; i < obj.length; i++) {
          let max = 0;
          let sum = 0;
          temp = obj[i];
          let resultTemp = []
          for (let j = 0; j < temp.value.length; j++) {
            sum += temp.value[i];
            resultTemp.push({name: nameData[j], value: temp.value[j]})
          }
          result[temp.time] = {value: resultTemp, sum: sum};
        }
        return result;
      }

      dataTempB = dataFormatter(dataTempB, name)
      dataTempL = dataFormatter(dataTempL, name)
      dataTempD = dataFormatter(dataTempD, name)
      for (let i in dataTempB) {
        dataResult1.push({
          title: {text: i + "三餐消费"},
          series: [
            {data: dataTempB[i].value},
            {data: dataTempL[i].value},
            {data: dataTempD[i].value},
            // {
            //   data: [
            //     {name: '早餐', value: dataTempB[i].sum},
            //     {name: '午餐', value: dataTempL[i].sum},
            //     {name: '晚餐', value: dataTempD[i].sum},
            //   ]
            // }
          ]
        })
      }
      console.log(dataResult1)
      this.echarts1.setOption({
        baseOption: {
          timeline: {
            data: date,
          },
          xAxis: [{
            'data': name,
          }]
        },
        options: dataResult1
      });
      this.echarts2.setOption({
        legend: {
          data: name
        },
        series: [
          {
            name: '雷达图',
            type: 'radar',
            emphasis: {
              lineStyle: {
                width: 4
              }
            },
            data: dataResult2
          },
        ]
      });
      this.dataResult3 = dataResult3;
      this.echarts1.hideLoading();
      this.echarts2.hideLoading();
    },
    fetchData3(){
      let data = [];
      for (let i = 0; i < this.dataResult3.length; i++) {
        if (this.dataResult3[i].name === this.nameData) {
          data = this.dataResult3[i].source;
          break;
        }
      }
      this.echarts3.setOption({
        dataset: {
          source: data
        },
      })
    }
  }
}
</script>

<style scoped>

</style>
