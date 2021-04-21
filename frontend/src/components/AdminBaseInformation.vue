<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="学生管理" name="first">
        <el-table
          :data="studentData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()) || data.sid.toLowerCase().includes(search.toLowerCase()))"
          v-loading="studentLoading"
          style="width: 100%">
          <el-table-column
            label="序号"
            type="index"
            align="center"
            width="80">
          </el-table-column>
          <el-table-column>
            <template slot="header" slot-scope="scope">
              <el-form :inline="true" :model="StudentQueryForm" class="demo-form-inline">
                <el-form-item label="学院:" :label-width="'120px'" style="text-align: left">
                  <el-select v-model="StudentQueryForm.collegeId" clearable placeholder="请选择">
                    <el-option
                      v-for="item in CollegeData"
                      :key="item.id"
                      :label="item.college"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="专业" :label-width="'120px'" style="text-align: left">
                  <el-select v-model="StudentQueryForm.majorId" clearable placeholder="请选择">
                    <el-option
                      v-for="item in MajorData"
                      v-show="item.collegeId === StudentQueryForm.collegeId"
                      :key="item.id"
                      :label="item.major"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="班级" :label-width="'120px'" style="text-align: left">
                  <el-select v-model="StudentQueryForm.classId" clearable placeholder="请选择">
                    <el-option
                      v-for="item in ClassData"
                      v-show="item.majorId === StudentQueryForm.majorId"
                      :key="item.id"
                      :label="item.className"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="StudentQuerySubmit('StudentQueryForm')">查询</el-button>
                </el-form-item>
              </el-form>
            </template>
            <el-table-column
              prop="sid"
              label="学号"
              align="center"
              width="100">
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名"
              align="center"
              width="100">
            </el-table-column>
            <el-table-column
              prop="className"
              label="班级"
              align="center"
              width="150">
            </el-table-column>
            <el-table-column
              prop="major"
              label="专业"
              align="center"
              width="180">
            </el-table-column>
            <el-table-column
              prop="college"
              label="学院"
              align="center"
              width="250">
            </el-table-column>
            <el-table-column
              prop="sex"
              label="性别"
              align="center"
              width="80">
            </el-table-column>
            <el-table-column
              align="right">
              <template slot="header" slot-scope="scope">
                <el-row :gutter="20">
                  <el-col :span="16">
                    <div class="grid-content bg-purple">
                      <el-input
                        v-model="search"
                        size="small"
                        clearable
                        prefix-icon="el-icon-search"
                        placeholder="输入关键字搜索"/>
                    </div>
                  </el-col>
                  <el-col :span="8">
                    <div class="grid-content bg-purple">
                      <el-button type="primary" icon="el-icon-circle-plus-outline" :loading="addStudentLoading"
                                 @click="addStudentFormVisible = true">添加
                      </el-button>
                    </div>
                  </el-col>
                </el-row>
              </template>
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="studentHandleEdit(scope.$index, scope.row)">Edit
                </el-button>
                <el-button
                  size="mini"
                  type="danger"
                  @click="studentHandleDelete(scope.$index, scope.row)">Delete
                </el-button>
              </template>
            </el-table-column>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="商户管理" name="second">
        <el-table
          :data="StoreData.filter(data => !search || data.storeName.toLowerCase().includes(search.toLowerCase()))"
          highlight-current-row
          style="width: 90%;margin-left: 50px;">
          <el-table-column
            label="序号"
            type="index"
            width="200">
          </el-table-column>
          <el-table-column
            label="商户"
            width="500"
            prop="storeName">
          </el-table-column>
          <el-table-column
            align="right">
            <template slot="header" slot-scope="scope">
              <el-row :gutter="20">
                <el-col :span="16">
                  <div class="grid-content bg-purple">
                    <el-input
                      v-model="search"
                      clearable
                      prefix-icon="el-icon-search"
                      placeholder="输入关键字搜索"/>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="grid-content bg-purple">
                    <el-button type="primary" icon="el-icon-circle-plus-outline" :loading="addStoreLoading"
                               @click="addStoreFormVisible = true">添加
                    </el-button>
                  </div>
                </el-col>
              </el-row>
            </template>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="StoreHandleEdit(scope.$index, scope.row)">Edit
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="StoreHandleDelete(scope.$index, scope.row)">Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
<!--      <el-tab-pane label="消费管理" name="third">-->
<!--        -->
<!--      </el-tab-pane>-->
      <el-tab-pane label="班级管理" name="fourth">
        <el-table
          :data="ClassData.filter(data => !search || data.college.toLowerCase().includes(search.toLowerCase())||data.major.toLowerCase().includes(search.toLowerCase())||data.className.toLowerCase().includes(search.toLowerCase()))"
          highlight-current-row
          v-loading="classLoading"
          style="width: 90%;margin-left: 50px;">
          <el-table-column
            label="序号"
            type="index"
            width="100">
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
            label="班级"
            width="200"
            prop="className">
          </el-table-column>
          <el-table-column
            align="right">
            <template slot="header" slot-scope="scope">
              <el-row :gutter="20">
                <el-col :span="16">
                  <div class="grid-content bg-purple">
                    <el-input
                      v-model="search"
                      clearable
                      prefix-icon="el-icon-search"
                      placeholder="输入关键字搜索"/>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div class="grid-content bg-purple">
                    <el-button type="primary" icon="el-icon-circle-plus-outline" :loading="addClassLoading"
                               @click="addClassFormVisible = true">添加
                    </el-button>
                  </div>
                </el-col>
              </el-row>
            </template>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="ClassHandleEdit(scope.$index, scope.row)">Edit
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="ClassHandleDelete(scope.$index, scope.row)">Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>
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
                      clearable
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
                      clearable
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
    <!--学生-->
    <el-popover
      width="160"
      v-model="deleteStudentVisible">
      <p>请确认是否删除{{ row.name }}({{ row.sid }})</p>
      <div style="text-align: right; margin: 0">
        <el-button size="mini" type="text" @click="deleteStudentVisible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="deleteStudent">确定</el-button>
      </div>
    </el-popover>
    <el-dialog title="修改学生信息" :visible.sync="updateStudentFormVisible" :rules="rules">
      <el-form :model="StudentForm" ref="StudentForm">
        <el-form-item label="学号" :label-width="'120px'" prop="sid" style="text-align: left;width: 48%">
          <el-input v-model="StudentForm.sid" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="'120px'" prop="name" style="text-align: left;width: 48%">
          <el-input v-model="StudentForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学院" :label-width="'120px'" style="text-align: left;width: 48%">
          <el-select v-model="StudentForm.collegeId" clearable placeholder="请选择">
            <el-option
              v-for="item in CollegeData"
              :key="item.id"
              :label="item.college"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" :label-width="'120px'" style="text-align: left;width: 48%">
          <el-select v-model="StudentForm.majorId" clearable placeholder="请选择">
            <el-option
              v-for="item in MajorData"
              v-show="item.collegeId === StudentForm.collegeId"
              :key="item.id"
              :label="item.major"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" :label-width="'120px'" style="text-align: left;width: 48%">
          <el-select v-model="StudentForm.classId" clearable placeholder="请选择">
            <el-option
              v-for="item in ClassData"
              v-show="item.majorId === StudentForm.majorId"
              :key="item.id"
              :label="item.className"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateStudentFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateStudent('StudentForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加学生" :visible.sync="addStudentFormVisible">
      <el-form :model="StudentForm" ref="StudentForm" :rules="rules">
        <el-form-item label="学号" :label-width="'120px'" prop="sid" style="text-align: left;width: 48%">
          <el-input v-model="StudentForm.sid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="'120px'" prop="name" style="text-align: left;width: 48%">
          <el-input v-model="StudentForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学院" :label-width="'120px'" style="text-align: left;width: 48%">
          <el-select v-model="StudentForm.collegeId" clearable placeholder="请选择">
            <el-option
              v-for="item in CollegeData"
              :key="item.id"
              :label="item.college"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" :label-width="'120px'" style="text-align: left;width: 48%">
          <el-select v-model="StudentForm.majorId" clearable placeholder="请选择">
            <el-option
              v-for="item in MajorData"
              v-show="item.collegeId === StudentForm.collegeId"
              :key="item.id"
              :label="item.major"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" :label-width="'120px'" style="text-align: left;width: 48%">
          <el-select v-model="StudentForm.classId" clearable placeholder="请选择">
            <el-option
              v-for="item in ClassData"
              v-show="item.majorId === StudentForm.majorId"
              :key="item.id"
              :label="item.className"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" :label-width="'120px'" style="text-align: left;width: 48%">
          <el-select v-model="StudentForm.sex" clearable placeholder="请选择">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="addStudentFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addStudent('StudentForm')">确 定</el-button>
      </div>
    </el-dialog>
    <!--商户-->
    <el-popover
      placement="top"
      width="160"
      v-model="deleteStoreVisible">
      <p>请确认是否删除{{ row.storeName }}</p>
      <div style="text-align: right; margin: 0">
        <el-button size="mini" type="text" @click="deleteStoreVisible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="deleteStore">确定</el-button>
      </div>
    </el-popover>
    <el-dialog title="修改商户信息" :visible.sync="updateStoreFormVisible">
      <el-form :model="storeForm" ref="storeForm" :rules="rules">
        <el-form-item label="商户名" :label-width="'120px'" prop="storeName">
          <el-input v-model="storeForm.storeName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateStoreFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateStore('storeForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加商户" :visible.sync="addStoreFormVisible">
      <el-form :model="storeForm" ref="storeForm" :rules="rules">
        <el-form-item label="商户名" :label-width="'120px'" prop="storeName">
          <el-input v-model="storeForm.storeName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addStoreFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addStore('storeForm')">确 定</el-button>
      </div>
    </el-dialog>

    <!--班级-->
    <el-popover
      width="160"
      v-model="deleteClassVisible">
      <p>请确认是否删除{{ row.className }}({{ row.major }})({{ row.college }})</p>
      <div style="text-align: right; margin: 0">
        <el-button size="mini" type="text" @click="deleteClassVisible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="deleteClass">确定</el-button>
      </div>
    </el-popover>
    <el-dialog title="修改班级信息" :visible.sync="updateClassFormVisible">
      <el-form :model="classForm" ref="classForm" :rules="rules">
        <el-form-item label="班级名" :label-width="'120px'" prop="className" style="width: 48%">
          <el-input v-model="classForm.className" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学院" :label-width="'120px'" style="text-align: left">
          <el-select v-model="classForm.collegeId" clearable placeholder="请选择">
            <el-option
              v-for="item in CollegeData"
              :key="item.id"
              :label="item.college"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" :label-width="'120px'" style="text-align: left">
          <el-select v-model="classForm.majorId" clearable placeholder="请选择">
            <el-option
              v-for="item in MajorData"
              v-show="item.collegeId === classForm.collegeId"
              :key="item.id"
              :label="item.major"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateClassFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateClass('classForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加班级" :visible.sync="addClassFormVisible">
      <el-form :model="classForm" ref="classForm" :rules="rules">
        <el-form-item label="班级名" :label-width="'120px'" prop="className" style="width: 48%">
          <el-input v-model="classForm.className" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="学院" :label-width="'120px'" style="text-align: left">
          <el-select v-model="classForm.collegeId" clearable placeholder="请选择">
            <el-option
              v-for="item in CollegeData"
              :key="item.id"
              :label="item.college"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" :label-width="'120px'" style="text-align: left">
          <el-select v-model="classForm.majorId" clearable placeholder="请选择">
            <el-option
              v-for="item in MajorData"
              v-show="item.collegeId === classForm.collegeId"
              :key="item.id"
              :label="item.major"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="addClassFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addClass('classForm')">确 定</el-button>
      </div>
    </el-dialog>

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
    <el-dialog title="修改专业信息" :visible.sync="updateMajorFormVisible">
      <el-form :model="majorForm" ref="majorForm" :rules="rules">
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
      <el-form :model="majorForm" ref="majorForm" :rules="rules">
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
    <el-dialog title="修改学院信息" :visible.sync="updateCollegeFormVisible">
      <el-form :model="form" :rules="rules">
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
      <el-form :model="form" ref="form" :rules="rules">
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
        callback(new Error('请输入'));
      }
      callback();
    };
    let validateSid = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入学号'));
      } else if (value.length !== 6) {
        callback(new Error('学号长度为6'));
      }
      callback();
    };
    return {
      CollegeData: [],
      MajorData: [],
      ClassData: [],
      StoreData: [],
      studentData: [],
      activeName: 'first',
      search: '',
      rules: {
        college: [
          {validator: validateCollegeName, trigger: 'blur'}
        ],
        major: [
          {validator: validateCollegeName, trigger: 'blur'}
        ],
        className: [
          {validator: validateCollegeName, trigger: 'blur'}
        ],
        storeName: [
          {validator: validateCollegeName, trigger: 'blur'}
        ],
        id: [
          {validator: validateSid, trigger: 'blur'}
        ],
        name: [
          {validator: validateCollegeName, trigger: 'blur'}
        ],
      },
      row: '',
      addCollegeLoading: false,
      addMajorLoading: false,
      addStudentLoading: false,
      addClassLoading: false,
      addStoreLoading: false,
      studentLoading: false,
      classLoading: true,
      addCollegeFormVisible: false,
      addMajorFormVisible: false,
      addClassFormVisible: false,
      addStoreFormVisible: false,
      addStudentFormVisible: false,
      updateCollegeFormVisible: false,
      updateMajorFormVisible: false,
      updateClassFormVisible: false,
      updateStoreFormVisible: false,
      updateStudentFormVisible: false,
      deleteVisible: false,
      deleteMajorVisible: false,
      deleteClassVisible: false,
      deleteStoreVisible: false,
      deleteStudentVisible: false,
      form: {
        college: ''
      },
      majorForm: {
        major: '',
        collegeId: ''
      },
      classForm: {
        className: '',
        majorId: '',
        collegeId: ''
      },
      storeForm: {
        storeName: '',
      },
      StudentQueryForm: {
        collegeId: '',
        majorId: '',
        classId: '',
      },
      StudentForm: {
        id: '',
        name: '',
        classId: '',
        majorId: '',
        collegeId: '',
        sex: '',
      }
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
    this.$axios.get("http://localhost:9090/class/getAll").then(function (response) {
      let data = response.data
      if (data.code === 1) {
        _this.ClassData = data.data;
        _this.classLoading = false;
      } else {
        alert(data.message)
      }
    }).catch(function (error) {
      console.log(error)
    });
    this.$axios.get("http://localhost:9090/store/getAll").then(function (response) {
      let data = response.data
      if (data.code === 1) {
        _this.StoreData = data.data;
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
    },
    ClassHandleEdit(index, row) {
      this.row = row;
      this.classForm = {
        className: row.className,
        collegeId: row.collegeId,
        majorId: row.majorId,
      };
      this.updateClassFormVisible = true;
    },
    ClassHandleDelete(index, row) {
      this.row = row
      this.deleteClassVisible = true
    },
    updateClass(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/class/update", {
            id: _this.row.id,
            className: _this.classForm.className,
            majorId: _this.classForm.majorId
          }).then(function (response) {
            let data = response.data;
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.updateClassFormVisible = false;
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
    addClass(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/class/add", {
            name: _this.classForm.className,
            majorId: _this.classForm.majorId
          }).then(function (response) {
            let data = response.data;
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.addClassFormVisible = false;
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
    deleteClass() {
      let _this = this
      this.$axios.post("http://localhost:9090/class/delete", {
        id: this.row.id,
        className: this.row.className,
        majorId: this.row.majorId
      }).then(function (response) {
        let data = response.data
        alert(data.message);
      }).catch(function (error) {
        console.log(error)
      })
      this.deleteVisible = false;
    },
    StoreHandleEdit(index, row) {
      this.row = row;
      this.updateStoreFormVisible = true;
      this.storeForm.storeName = row.name;
    },
    StoreHandleDelete(index, row) {
      this.row = row;
      this.deleteStoreVisible = true;
    },
    deleteStore() {
      let _this = this
      this.$axios.post("http://localhost:9090/store/delete", {
        id: this.row.id,
        storeName: this.row.storeName
      }).then(function (response) {
        let data = response.data
        alert(data.message);
      }).catch(function (error) {
        console.log(error)
      })
      this.deleteStoreVisible = false;
    },
    updateStore(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/store/update", {
            id: _this.row.id,
            storeName: _this.storeForm.storeName
          }).then(function (response) {
            let data = response.data;
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.updateStoreFormVisible = false;
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
    addStore(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/store/add", {
            storeName: _this.storeForm.storeName
          }).then(function (response) {
            let data = response.data;
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.addStoreFormVisible = false;
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
    studentHandleEdit(index, row) {
      this.row = row;
      this.updateStudentFormVisible = true;
      this.StudentForm = {
        sid: row.sid,
        name: row.name,
        classId: row.classId,
        majorId: row.majorId,
        collegeId: row.collegeId,
        sex: row.sex,
      }
    },
    studentHandleDelete(index, row) {
      this.deleteStudentVisible = true;
      this.row = row;
    },
    StudentQuerySubmit(formName) {
      this.studentLoading = true;
      let _this = this;
      this.$axios.post("http://localhost:9090/student/getByClassId", {
        id: _this.StudentQueryForm.classId,
      }).then(function (response) {
        if (response.data.code === 1) {
          _this.studentData = response.data.data;
          _this.studentLoading = false
        } else {
          alert("加载失败");
        }
      }).catch(function (error) {
        console.log(error);
      })
    },
    deleteStudent() {
      let _this = this
      this.$axios.post("http://localhost:9090/student/delete", {
        id: this.row.sid,
      }).then(function (response) {
        let data = response.data
        alert(data.message);
      }).catch(function (error) {
        console.log(error)
      })
      this.deleteStudentVisible = false;
    },
    addStudent(formName) {
      this.addStudentLoading = true;
      let _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/student/add", {
            id: this.StudentForm.sid,
            name: this.StudentForm.name,
            classId: this.StudentForm.classId,
            majorId: this.StudentForm.majorId,
            collegeId: this.StudentForm.collegeId,
            sex: this.StudentForm.sex,
          }).then(function (response) {
            let data = response.data;
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.addStudentFormVisible = false;
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
        _this.addStudentLoading = false;
      });
    },
    updateStudent(formName) {
      let _this = this;
      console.log(this.StudentForm)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:9090/student/update", {
            id: this.StudentForm.sid,
            name: this.StudentForm.name,
            classId: this.StudentForm.classId,
            majorId: this.StudentForm.majorId,
            collegeId: this.StudentForm.collegeId,
            sex: this.StudentForm.sex,
          }).then(function (response) {
            let data = response.data;
            if (data.code === 1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
              _this.updateStudentFormVisible = false;
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
  },
}
</script>
<style scoped>
</style>
