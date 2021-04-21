<template>
  <el-container>
    <el-header style="text-align: left;margin-left: 20px">
      <el-form :inline="true" :model="formInline" class="demo-form-inline" :rules="rules" ref="formInline">
        <el-form-item label="级别" prop="id">
          <el-cascader
            style="width: auto"
            placeholder="请选择学院或专业或班级"
            :props="props"
            :model="formInline.id"
            collapse-tags
            ref="id"
            clearable></el-cascader>
        </el-form-item>
        <el-form-item label="选择" prop="choice">
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
    </el-header>
    <el-main>
      <div>
        <div id="chart" ref="chart"></div>
        <div id="chart1" ref="chart"></div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import axios from 'axios'

const echarts = require('echarts/lib/echarts');
require('echarts/lib/component/title');
require('echarts/lib/component/toolbox');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/grid');
require('echarts/lib/component/legend');
require('echarts/lib/chart/line');

export default {
  name: "OneDay",
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
        }, {
          text: '最近六个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setMonth(start.getMonth() - 6);
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
      monthData: [],
      myChart: '',
      // myChart1: ''
    };
  },
  mounted() {
    this.initChart();
    // this.initChart1();
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
          axios.post('http://localhost:9090/schoolUser/getConsume', {
            id: obj,
            choice: _this.formInline.choice,
            date: _this.formInline.date
          }).then(function (response) {
            _this.monthData = response.data.data;
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
    initChart() {
      this.myChart = echarts.init(document.getElementById('chart'));
      let option = {
        title: {
          text: '消费数据统计图'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: []
      };
      this.myChart.setOption(option);
      this.myChart.hideLoading()
    },
    // initChart1() {
    //   this.myChart1 = echarts.init(document.getElementById('chart1'));
    //   let option = {
    //     title: {
    //       text: '消费统计图'
    //     },
    //     tooltip: {
    //       trigger: 'axis'
    //     },
    //     legend: {
    //       data: []
    //     },
    //     grid: {
    //       left: '3%',
    //       right: '4%',
    //       bottom: '3%',
    //       containLabel: true
    //     },
    //     toolbox: {
    //       feature: {
    //         saveAsImage: {}
    //       }
    //     },
    //     xAxis: {
    //       type: 'category',
    //       boundaryGap: false,
    //       data: []
    //     },
    //     yAxis: {
    //       type: 'value'
    //     },
    //     series: []
    //   };
    //   this.myChart1.setOption(option);
    //   this.myChart1.hideLoading()
    // },
    fetchData() {
      this.myChart.showLoading();
      let name = [];
      this.monthData.forEach(item => (
        name.push(item.name)
      ));
      let date = new Set();
      this.monthData.forEach(item => (
        item.consumeMonthData.forEach(item1 => (
          date.add(item1.year + "-" + item1.month)
        ))
      ));
      date = Array.from(date).sort();
      let dataResult = [];
      for (let i = 0; i < this.monthData.length; i++) {
        let temp = {
          name: this.monthData[i].name,
          type: 'line',
          // stack: '平均人次消费',
          data: Array.from({length: date.length}).map(item => (0)),
        };
        for (let j = 0; j < this.monthData[i].consumeMonthData.length; j++) {
          let tempDate = this.monthData[i].consumeMonthData[j].year + '-' + this.monthData[i].consumeMonthData[j].month
          for (let k = 0; k < date.length; k++) {
            if (tempDate === date[k]) {
              temp.data[k] = this.monthData[i].consumeMonthData[j].consumption_average_money;
              break;
            }
          }
        }
        dataResult.push(temp);
      }
      this.myChart.setOption({
        legend: {
          data: name,
        },
        xAxis: {
          data: date,
        },
        series: dataResult,
      });
      this.myChart.hideLoading();
    },
  }
}
</script>

<style scoped>
#chart {
  width: 600px;
  height: 400px;
  /* background-color:black;  */
  margin-top: 20px;
  float: left;
  margin-left: 20px;
}

/*#chart1 {*/
/*  width: 600px;*/
/*  height: 400px;*/
/*  float: left;*/
/*  !* background-color:black;  *!*/
/*  margin-top: 20px;*/
/*  margin-left: 20px;*/
/*}*/
</style>
