<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="学生管理" name="first">

      </el-tab-pane>
      <el-tab-pane label="商户管理" name="second">

      </el-tab-pane>
      <el-tab-pane label="消费管理" name="third">

      </el-tab-pane>
      <el-tab-pane label="班级管理" name="fourth">

      </el-tab-pane>
      <el-tab-pane label="专业管理" name="fifth">
        <el-table
          :data="MajorData.filter(data => !search || data.college.toLowerCase().includes(search.toLowerCase())||data.major.toLowerCase().includes(search.toLowerCase()))"
          highlight-current-row
          style="width: 90%;margin-left: 50px;">
          <el-table-column
            label="序号"
            type="index"
            width="200">
          </el-table-column>
          <el-table-column
            label="学院名"
            width="250"
            prop="college">
          </el-table-column>
          <el-table-column
            label="专业"
            width="250"
            prop="major">
          </el-table-column>
          <el-table-column
            align="right">
            <template slot="header" slot-scope="scope">
              <el-row :gutter="20">
                <el-col :span="16">
                  <div class="grid-content bg-purple">
                    <el-input
                      v-model="search"
                      prefix-icon="el-icon-search"
                      placeholder="输入关键字搜索"/>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="grid-content bg-purple">
                    <el-button type="primary" icon="el-icon-circle-plus-outline" :loading="addMajorLoading"
                               @click="addMajorFormVisible = true">添加
                    </el-button>
                  </div>
                </el-col>
              </el-row>
            </template>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="majorHandleEdit(scope.$index, scope.row)">Edit
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="majorHandleDelete(scope.$index, scope.row)">Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-tab-pane>
      <el-tab-pane label="学院管理" name="sixth">
        <el-table
          :data="CollegeData.filter(data => !search || data.college.toLowerCase().includes(search.toLowerCase()))"
          highlight-current-row
          style="width: 90%;margin-left: 50px;">
          <el-table-column
            label="序号"
            type="index"
            width="200">
          </el-table-column>
          <el-table-column
            label="学院名"
            width="500"
            prop="college">
          </el-table-column>
          <el-table-column
            align="right">
            <template slot="header" slot-scope="scope">
              <el-row :gutter="20">
                <el-col :span="16">
                  <div class="grid-content bg-purple">
                    <el-input
                      v-model="search"
                      prefix-icon="el-icon-search"
                      placeholder="输入关键字搜索"/>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="grid-content bg-purple">
                    <el-button type="primary" icon="el-icon-circle-plus-outline" :loading="addCollegeLoading"
                               @click="addCollegeFormVisible = true">添加
                    </el-button>
                  </div>
                </el-col>
              </el-row>
            </template>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="CollegeHandleEdit(scope.$index, scope.row)">Edit
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="CollegeHandleDelete(scope.$index, scope.row)">Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>


    <!--专业-->
    <el-popover
      width="160"
      v-model="deleteMajorVisible">
      <p>请确认是否删除{{ row.major }}({{ row.college }})</p>
      <div style="text-align: right; margin: 0">
        <el-button size="mini" type="text" @click="deleteMajorVisible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="deleteMajor">确定</el-button>
      </div>
    </el-popover>
    <el-dialog title="修改专业名" :visible.sync="updateMajorFormVisible">
      <el-form :model="majorForm" ref="majorForm">
        <el-form-item label="专业名" :label-width="'120px'" prop="major" style="width: 48%">
          <el-input v-model="majorForm.major" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学院" :label-width="'120px'" style="text-align: left">
          <el-select v-model="majorForm.collegeId" clearable placeholder="请选择">
            <el-option
              v-for="item in CollegeData"
              :key="item.id"
              :label="item.college"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateMajorFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateMajor('majorForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加专业" :visible.sync="addMajorFormVisible">
      <el-form :model="majorForm" ref="majorForm">
        <el-form-item label="专业名" :label-width="'120px'" prop="major" style="width: 48%">
          <el-input v-model="majorForm.major" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学院" :label-width="'120px'" style="text-align: left">
          <el-select v-model="majorForm.collegeId" clearable placeholder="请选择">
            <el-option
              v-for="item in CollegeData"
              :key="item.id"
              :label="item.college"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addMajorFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMajor('majorForm')">确 定</el-button>
      </div>
    </el-dialog>

    <!--学院-->
    <el-popover
      placement="top"
      width="160"
      v-model="deleteVisible">
      <p>请确认是否删除{{ row.college }}</p>
      <div style="text-align: right; margin: 0">
        <el-button size="mini" type="text" @click="deleteVisible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="deleteCollege">确定</el-button>
      </div>
    </el-popover>
    <el-dialog title="修改学院名" :visible.sync="updateCollegeFormVisible">
      <el-form :model="form">
        <el-form-item label="活动名称" :label-width="'120px'" prop="college">
          <el-input v-model="form.college" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateCollegeFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateCollege('form')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加学院" :visible.sync="addCollegeFormVisible">
      <el-form :model="form" ref="form">
        <el-form-item label="学院名" :label-width="'120px'" prop="college">
          <el-input v-model="form.college" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addCollegeFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCollege('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "AdminBaseInformation",
  data() {
    let validateCollegeName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入昵称'));
      }
      callback();
    };
    return {
      CollegeData: [],
      MajorData: [],
      activeName: 'first',
      search: '',
      addCollegeLoading: false,
      addCollegeFormVisible: false,
      updateCollegeFormVisible: false,
      addMajorFormVisible: false,
      addMajorLoading: false,
      updateMajorFormVisible: false,
      deleteVisible: false,
      deleteMajorVisible: false,
      form: {
        college: ''
      },
      rules: {
        college: [
          {validator: validateCollegeName, trigger: 'blur'}
        ],
        major: [
          {validator: validateCollegeName, trigger: 'blur'}
        ],
      },
      row: '',
      majorForm: {
        major: '',
        collegeId: ''
      },

    };
  },
  mounted() {
    let _this = this;
    this.$axios.get("http://localhost:9090/college/getAll").then(function (response) {
      let data = response.data
      if (data.code === 1) {
        _this.CollegeData = data.data;
      } else {
        alert(data.message);
      }
    }).catch(function (error) {
      console.log(error)
    });
    this.$axios.get("http://localhost:9090/major/getAll").then(function (response) {
      let data = response.data
      if (data.code === 1) {
        _this.MajorData = data.data;
      } else {
        alert(data.message)
      }
    }).catch(function (error) {
      console.log(error)
    });
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    CollegeHandleEdit(index, row) {
      this.row = row;
      this.updateCollegeFormVisible = true;
      this.form.college = row.name;
    },
    majorHandleEdit(index, row) {
      this.row = row;
      this.updateMajorFormVisible = true;
      this.majorForm = {
        collegeId: row.collegeId,
        major: row.major
      }
    },
    majorHandleDelete(index, row) {
      this.row = row
      this.deleteMajorVisible = true
    },
    CollegeHandleDelete(index, row) {
      this.row = row;
      this.deleteVisible = true;
    },
    updateCollege(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/college/update", {
            id: _this.row.id,
            college: formName.college
          }).then(function (response) {
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.updateCollegeFormVisible = false;
            } else {
              alert(data.message);
            }
          }).catch(function (error) {
            console.log(error);
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    addCollege(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/college/add", {
            college: _this.form.college
          }).then(function (response) {
            let data = response.data;
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.addCollegeFormVisible = false;
            } else {
              alert(data.message);
            }
          }).catch(function (error) {
            console.log(error)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    addMajor(formName) {
      console.log(formName.collegeId)
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/major/add", {
            collegeId: _this.majorForm.collegeId,
            major: _this.majorForm.major
          }).then(function (response) {
            let data = response.data;
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.addMajorFormVisible = false;
            } else {
              alert(data.message);
            }
          }).catch(function (error) {
            console.log(error)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    updateMajor(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/major/update", {
            id: _this.row.id,
            collegeId: _this.majorForm.collegeId,
            major: _this.majorForm.major
          }).then(function (response) {
            let data = response.data
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.updateMajorFormVisible = false;
            } else {
              alert(data.message);
            }
          }).catch(function (error) {
            console.log(error);
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    deleteCollege() {
      let _this = this
      this.$axios.post("http://localhost:9090/college/delete", {
        id: this.row.id,
        college: this.row.college
      }).then(function (response) {
        let data = response.data
        alert(data.message);
      }).catch(function (error) {
        console.log(error)
      })
      this.deleteVisible = false;
    },
    deleteMajor() {
      let _this = this
      this.$axios.post("http://localhost:9090/major/delete", {
        id: this.row.id,
        collegeId: this.row.collegeId,
        major: this.row.major
      }).then(function (response) {
        let data = response.data
        alert(data.message);
      }).catch(function (error) {
        console.log(error)
      })
      this.deleteMajorVisible = false;
    }


  },
}
</script>

<style scoped>


</style>
