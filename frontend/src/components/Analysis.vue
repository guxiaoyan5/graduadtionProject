<template>
  <div style="margin-left: 30px">
    <el-row :gutter="20" style="margin-top: 10px;height: 540px">
      <el-col :span="24" style="height: 500px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart1" ref="chart1" style="height:100%;width: 100%"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height:500px">
      <el-col :span="24" style="height: 450px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:90%;width: 100%;padding:0px">
          <div slot="header" class="clearfix">
            贫困学生信息
          </div>
          <el-table
            :data="tableData"
            style="width: 100%;text-align: center"
            height="500">
            <el-table-column
              type="index"
              width="50"
              align="center">
            </el-table-column>
            <el-table-column
              prop="sid"
              label="学号"
              width="150"
              align="center">
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名"
              width="150"
              align="center">
            </el-table-column>
            <el-table-column
              prop="class"
              label="班级"
              width="150"
              align="center">
            </el-table-column>
            <el-table-column
              prop="major"
              label="专业"
              width="300"
              align="center">
            </el-table-column>
            <el-table-column
              prop="college"
              label="学院"
              width="300"
              align="center">
            </el-table-column>
            <el-table-column
              prop="sex"
              label="性别"
              width="50"
              align="center">
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

const echarts = require('echarts/lib/echarts');
require('echarts/lib/component/toolbox');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/grid');
require('echarts/lib/component/legend');
require('echarts/lib/chart/bar');
require('echarts/lib/chart/line');

export default {
  name: "Analysis",
  data() {
    let that = this;
    axios.get('http://localhost:9090/schoolUser2/getAnalysis').then(function (response) {
      that.dataTotal = response.data.data;

      that.echarts1 = echarts.init(document.getElementById('chart1'));
      let colors = ['#5470C6', '#91CC75', '#EE6666'];
      let option = {
        color: colors,

        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        legend: {
          data: ['平均人次消费', '总消费', '消费次数']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: []
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '平均人次消费',
            position: 'right',
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[0]
              }
            },
            axisLabel: {
              formatter: '{value} 元'
            }
          },
          {
            type: 'value',
            name: '总消费',
            position: 'right',
            offset: 80,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[1]
              }
            },
            axisLabel: {
              formatter: '{value} 元'
            }
          },
          {
            type: 'value',
            name: '消费次数',
            position: 'left',
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value} 人次'
            }
          }
        ],
        series: [
          {
            name: '平均人次消费',
            type: 'bar',
            data: []
          },
          {
            name: '总消费',
            type: 'bar',
            yAxisIndex: 1,
            data: []
          },
          {
            name: '消费次数',
            type: 'line',
            yAxisIndex: 2,
            data: []
          }
        ]
      };
      that.echarts1.setOption(option);

      let name = []
      let data1 = []
      let data2 = []
      let data3 = []
      that.tableData=[]
      for(let i=0;i<that.dataTotal.length;i++){
        let temp = {
          'name':that.dataTotal[i].name,
          'sid':that.dataTotal[i].sid,
          'class':that.dataTotal[i].className,
          'major':that.dataTotal[i].major,
          'college':that.dataTotal[i].college,
          'sex':that.dataTotal[i].sex,
        }
        name.push(that.dataTotal[i].name)
        data1.push(that.dataTotal[i].consumption_average_money.toFixed(2))
        data2.push(that.dataTotal[i].consumption_total_money.toFixed(2))
        data3.push(that.dataTotal[i].consumption_count)
        that.tableData.push(temp);
      }
      that.echarts1.setOption({
        xAxis: [{
          data:name
        }],
        series:[{
          data: data1
        },{
          data: data2
        },{
          data: data3
        }]
      })
    }).catch(function (error) {
      console.log('error!!');
      console.log(error)
    });
    return {
      dataTotal: that.dataTotal,
      echarts1: that.echarts1,
      tableData: that.tableData,
    };
  }
}
</script>

<style scoped>

</style>
