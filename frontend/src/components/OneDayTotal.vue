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
          <div id="chart2" ref="chart2" style="height:100%;width: 100%;"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height:500px">
      <el-col :span="24" style="height: 450px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:90%;width: 100%;padding:0px">
          <div id="chart3" ref="chart3" style="height:100%;width: 100%;"></div>
        </el-card>
      </el-col>
<!--      <el-col :span="10" style="height: 450px">-->
<!--        <el-card style="height:inherit;width: content-box;padding: 20px"-->
<!--                 body-style="height:90%;width: 100%;padding:0px">-->
<!--          <div id="chart4" ref="chart4" style="height:100%;width: 90%;"></div>-->
<!--        </el-card>-->
<!--      </el-col>-->
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

const echarts = require('echarts/lib/echarts');
require('echarts/lib/component/polar');
require('echarts/lib/component/legend');
require('echarts/lib/chart/bar');
require('echarts/lib/component/tooltip');
require('echarts/lib/chart/pie');
require('echarts/lib/component/toolbox');
require('echarts/lib/component/grid');
require('echarts/lib/chart/line');
require('echarts/lib/chart/radar');
export default {
  name: "OneDayTotal",
  data() {
    return {
      rules: {
        // id: [{required: true, message: '请选择级别', trigger: 'change'}],
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
      Data: [],
      echarts1: '',
      echarts2: '',
      echarts3: '',
      echarts4: '',
    };
  },
  mounted() {
    this.initChart1();
    this.initChart2();
    this.initChart3();
    // this.initChart4();
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
          axios.post('http://localhost:9090/schoolUser2/getConsume', {
            id: obj,
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
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        toolbox: {
          feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        legend: {
          data: ['人均消费', '消费人数']
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '消费金额',
            axisLabel: {
              formatter: '{value} 元'
            }
          },
          {
            type: 'value',
            name: '人数',
            position: 'right',
            axisLine: {
              show: true,
            },
            axisLabel: {
              formatter: '{value} '
            }
          }
        ],
        series: [
          {
            name: '人均消费',
            type: 'bar',
            data: []
          },
          {
            name: '消费人数',
            type: 'bar',
            data: []
          },
        ]
      };
      this.echarts1.setOption(option);
      this.echarts1.hideLoading();
    },
    initChart2() {
      this.echarts2 = echarts.init(document.getElementById('chart2'));
      let option = {
        title: {text: '人均消费占比'},
        color: ['#67F9D8', '#FFE434'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        radiusAxis: {
          show: false,
          splitLine: {show: false}
        },
        angleAxis: {
          type: 'category',
          data: []
        },
        polar: {},
        series: [],
        legend: {
          show: true,
          data: ['低于平均人次消费次数', '高于平均人次消费次数']
        }
      };
      this.echarts2.setOption(option);
      this.echarts2.hideLoading();
    },
    initChart3() {
      this.echarts3 = echarts.init(document.getElementById('chart3'));
      let option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          data: []
        },
        series: [
          {
            name: '消费总和',
            type: 'pie',
            selectedMode: 'single',
            radius: [0, '30%'],
            label: {
              position: 'inner',
              fontSize: 10,
            },
            labelLine: {
              show: false
            },
            data: []
          },
          {
            name: '每人次平均消费',
            type: 'pie',
            radius: ['45%', '60%'],
            labelLine: {
              length: 30,
            },
            label: {
              formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
              backgroundColor: '#F6F8FC',
              borderColor: '#8C8D8E',
              borderWidth: 1,
              borderRadius: 4,

              rich: {
                a: {
                  color: '#6E7079',
                  lineHeight: 22,
                  align: 'center'
                },
                hr: {
                  borderColor: '#8C8D8E',
                  width: '100%',
                  borderWidth: 1,
                  height: 0
                },
                b: {
                  color: '#4C5058',
                  fontSize: 14,
                  fontWeight: 'bold',
                  lineHeight: 33
                },
                per: {
                  color: '#fff',
                  backgroundColor: '#4C5058',
                  padding: [3, 4],
                  borderRadius: 4
                }
              }
            },
            data: []
          }
        ]
      };
      this.echarts3.setOption(option)
      this.echarts3.hideLoading();
    },
    // initChart4() {
    //   this.echarts4 = echarts.init(document.getElementById('chart4'));
    //   let option = {
    //     // color: [ '#67F9D8', '#FFE434', '#56A3F1', '#FF917C'],
    //     title: {
    //       text: '消费次数统计图'
    //     },
    //     legend: {
    //       data: ['消费总次数', '高于每人次消费次数', '低于每人次消费次数']
    //     },
    //     tooltip: {
    //       trigger: 'item',
    //     },
    //     radar: [
    //       {
    //         indicator: [],
    //         center: ['75%', '50%'],
    //         radius: 120,
    //         name: {
    //           textStyle: {
    //             color: '#fff',
    //             backgroundColor: '#666',
    //             borderRadius: 3,
    //             padding: [3, 5]
    //           }
    //         }
    //
    //       }
    //     ],
    //     series: [
    //       {
    //         name: '消费次数',
    //         type: 'radar',
    //         data: [
    //          ]
    //       }
    //     ]
    //   };
    //   this.echarts4.setOption(option);
    //   this.echarts4.hideLoading();
    // },
    fetchData(callbackfn, thisArg) {
      this.echarts1.showLoading();
      this.echarts2.showLoading();
      this.echarts3.showLoading();
      let name =  Array.from({length: 0});
      this.Data.forEach(item => name.push(item.name))
      let low2 = [], high2 = [];
      // let data_4_1 =  Array.from({length: 0});
      // let data_4_2 = Array.from({length: 0});
      // let data_4_3 =  Array.from({length: 0});
      // let data_4_4 = Array.from({length: 0});
      // this.Data.forEach(item => data_4_1.push({text: item.name}));
      // this.Data.forEach(item => data_4_2.push(item.consumeData[0].consumption_count));
      // this.Data.forEach(item => data_4_3.push(item.consumeData[0].consumption_high_count));
      // this.Data.forEach(item => data_4_4.push(item.consumeData[0].consumption_low_count));
      let data3_1 = Array.from({length: name.length}).map(item => ({value: 0, name: ''}));
      let data3_2 = Array.from({length: name.length}).map(item => ({value: 0, name: ''}));
      let data1_1 = Array.from({length: name.length}).map(item => (0));
      let data1_2 = Array.from({length: name.length}).map(item => (0));
      for (let i = 0; i < this.Data.length; i++) {
        low2.push(this.Data[i].consumeData[0].student_low_count);
        high2.push(this.Data[i].consumeData[0].student_high_count);
        data3_1[i].value = this.Data[i].consumeData[0].consumption_total_money;
        data3_2[i].value = this.Data[i].consumeData[0].consumption_average_money;
        data3_1[i].name = this.Data[i].name;
        data3_2[i].name = this.Data[i].name;
        data1_1[i] = this.Data[i].consumeData[0].consumption_student_average_money;
        data1_2[i] = this.Data[i].consumeData[0].student_count;
      }
      this.echarts1.setOption({
        xAxis: [
          {
            data: name,
          }
        ],
        series: [
          {
            data: data1_1
          },
          {
            yAxisIndex: 1,
            data: data1_2
          }
        ]
      })
      this.echarts2.setOption({
        angleAxis: {
          data: name,
        },
        series: [{
          type: 'bar',
          data: low2,
          coordinateSystem: 'polar',
          name: '低于平均人次消费次数',
          stack: 'a',
          emphasis: {
            focus: 'series'
          }
        }, {
          type: 'bar',
          data: high2,
          coordinateSystem: 'polar',
          name: '高于平均人次消费次数',
          stack: 'a',
          emphasis: {
            focus: 'series'
          }
        }],
      });
      this.echarts3.setOption({
        legend: {
          data: name,
        },
        series: [
          {
            data: data3_1,
          },
          {
            data: data3_2,
          }
        ]
      })
      // this.echarts4.setOption({
      //   radar: [{
      //     indicator: data_4_1,
      //   }],
      //   series: [{
      //     data: [
      //       {
      //         value: data_4_2,
      //         name: '消费总次数',
      //         label: {
      //           show: true,
      //           formatter: function (params) {
      //             return params.value;
      //           }
      //         }
      //       },
      //       {
      //         value: data_4_3,
      //         name: '高于每人次消费次数',
      //         label: {
      //           show: true,
      //           formatter: function (params) {
      //             return params.value;
      //           }
      //         }
      //       },
      //       {
      //         value: data_4_4,
      //         name: '低于每人次消费次数',
      //         label: {
      //           show: true,
      //           formatter: function (params) {
      //             return params.value;
      //           }
      //         }
      //       }
      //     ]
      //   }
      //   ]
      // });
      // this.echarts4.hideLoading();
      this.echarts1.hideLoading();
      this.echarts3.hideLoading();
      this.echarts2.hideLoading();
    },
  },
}
</script>

<style scoped>

</style>
