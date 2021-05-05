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
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart3" ref="chart3" style="height:100%;width: 100%;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

const echarts = require('echarts/lib/echarts');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/grid');
require('echarts/lib/component/legend');
require('echarts/lib/chart/bar');
require('echarts/lib/chart/pie');
require('echarts/lib/component/markLine');
export default {
  name: "ThreeMealsTotal",
  data() {
    return {
      rules: {
        // id: [{required: true, message: '请选择级别', trigger: 'change'}],
      },
      formInline: {
        id: '',
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
      Data: [],
      chart1: '',
      chart2: '',
      chart3: '',
      chart4: '',
    };
  },
  mounted() {
    this.initChart1();
    this.initChart3();
    this.initChart2();
    this.fetchData();
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
          axios.post('http://localhost:9090/schoolUser2/getThreeMeals', {
            id: obj
          }).then(function (response) {
            _this.Data = response.data.data;
            console.log(_this.Data)
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
      this.chart1 = echarts.init(document.getElementById('chart1'));
      let option = {
        color: ['#5470c6', '#91cc75', '#fac858'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {            // Use axis to trigger tooltip
            type: 'shadow'        // 'shadow' as default; can also be 'line' or 'shadow'
          }
        },
        legend: {
          data: ['早餐', '午餐', '晚餐']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: []
        },
        series: [
          {
            name: '早餐',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: []
          },
          {
            name: '午餐',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: []
          },
          {
            name: '晚餐',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: []
          }
        ]
      };
      this.chart1.setOption(option);
      this.chart1.hideLoading();
    },
    initChart2() {
      this.chart2 = echarts.init(document.getElementById('chart2'));
      let option = {
        title: {
          text: '人均消费统计图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '人均消费统计图',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '12',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: []
          }
        ]
      };
      this.chart2.setOption(option);
      this.chart2.hideLoading();
    },
    initChart3() {
      this.chart3 = echarts.init(document.getElementById('chart3'));
      let option = {
        color: ['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：l'ine' | 'shadow'
          }
        },
        legend: {
          data: ['高于人均消费次数', '低于人均消费次数', '高于平均每人次消费次数', '低于平均每人次消费次数', '学生人数']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: []
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '消费次数',
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '学生人数',
            position: 'right',
            axisLine: {
              show: true,
              lineStyle: {
                color: '#ea7ccc'
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
        ],
        series: [
          {
            name: '高于平均每人次消费次数',
            type: 'bar',
            barWidth: 10,
            stack: '消费次数',
            emphasis: {
              focus: 'series'
            },
            data: []
          },
          {
            name: '低于平均每人次消费次数',
            type: 'bar',
            stack: '消费次数',
            emphasis: {
              focus: 'series'
            },
            data: []
          },
          {
            name: '学生人数',
            type: 'bar',
            yAxisIndex: 1,
            emphasis: {
              focus: 'series'
            },
            data: []
          },
          {
            name: '高于人均消费次数',
            type: 'bar',
            barWidth: 8,
            stack: '学生人数',
            yAxisIndex: 1,
            emphasis: {
              focus: 'series'
            },
            data: []
          },
          {
            name: '低于人均消费次数',
            type: 'bar',
            stack: '学生人数',
            yAxisIndex: 1,
            emphasis: {
              focus: 'series'
            },
            data: []
          },
        ]
      };
      this.chart3.setOption(option);
      this.chart3.hideLoading();
    },
    fetchData() {
      this.chart1.showLoading();
      this.chart2.showLoading();
      this.chart3.showLoading();
      let name = Array.from({length: 0});
      this.Data.forEach(item => name.push(item.name));
      let B = Array.from({length: name.length}).map(item => (0));
      let L = Array.from({length: name.length}).map(item => (0));
      let D = Array.from({length: name.length}).map(item => (0));
      let temp2 = Array.from({length: 0});
      let temp3_1 = [];
      let temp3_2 = [];
      let temp3_3 = [];
      let temp3_4 = [];
      let temp3_5 = [];
      let temp3_6 = [];

      for (let i = 0; i < name.length; i++) {
        for (let j = 0; j < this.Data[i].consumeThreeData.length; j++) {
          if (this.Data[i].consumeThreeData[j].consumption_category === '早') {
            B[i] = this.Data[i].consumeThreeData[j].consumption_total_money
          } else if (this.Data[i].consumeThreeData[j].consumption_category === '午') {
            L[i] = this.Data[i].consumeThreeData[j].consumption_total_money
          } else {
            D[i] = this.Data[i].consumeThreeData[j].consumption_total_money
          }
          temp2.push({
            value: this.Data[i].consumeThreeData[j].consumption_student_average_money,
            name: this.Data[i].name + ' ' + this.Data[i].consumeThreeData[j].consumption_category + '餐'
          })
          temp3_1.push(this.Data[i].name + ' ' + this.Data[i].consumeThreeData[j].consumption_category + '餐')
          temp3_2.push(this.Data[i].consumeThreeData[j].student_count)
          temp3_3.push(this.Data[i].consumeThreeData[j].consumption_high_count)
          temp3_4.push(this.Data[i].consumeThreeData[j].consumption_low_count)
          temp3_5.push(this.Data[i].consumeThreeData[j].student_high_count)
          temp3_6.push(this.Data[i].consumeThreeData[j].student_low_count)
        }
      }
      this.chart1.setOption({
        yAxis: {
          type: 'category',
          data: name,
        },
        series: [
          {
            data: B
          },
          {
            data: L
          }, {
            data: D
          },
        ]
      })
      this.chart2.setOption({
        series: [
          {
            data: temp2
          }
        ]
      })
      this.chart3.setOption({
        xAxis: [
          {
            data: temp3_1
          }
        ],
        series: [
          {
            data: temp3_3
          },
          {
            data: temp3_4
          },
          {
            data: temp3_2
          },
          {
            data: temp3_5
          },
          {
            data: temp3_6
          },
        ]
      })
      this.chart1.hideLoading();
      this.chart2.hideLoading();
      this.chart3.hideLoading();
    }
  },
}
</script>

<style scoped>

</style>
