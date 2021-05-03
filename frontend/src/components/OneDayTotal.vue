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
            <el-select v-model="nameData" filterable placeholder="请选择" @change="fetchData3" style="float: right;"
                       size="mini">
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
    };
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
  },
}
</script>

<style scoped>

</style>
