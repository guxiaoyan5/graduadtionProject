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

    </el-main>
  </el-container>
</template>

<script>
import axios from 'axios'

let id = 0;
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
    };
  },
  methods: {
    onSubmit(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const nodes = _this.$refs['id'].getCheckedNodes();
          let obj = [];
          nodes.forEach(node=>{
            obj.push({
              value:node.value,
              label:node.label,
              level:node.level,
            })
          });
          axios.post('http://localhost:9090/schoolUser/getConsume', {
            id:obj,
            choice:_this.formInline.choice,
            date:_this.formInline.date
          }).then(function (response) {
            console.log(response.data)
          }).catch(function (error) {
            console.log('error!!');
            console.log(error)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
