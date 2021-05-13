import Vue from 'vue'
import Router from 'vue-router'
import SchoolLogin from "../view/school/Login";
import SchoolHome from "../view/school/Home";
import ChangePassword from "../components/ChangePassword";
import ChangeName from "../components/ChangeName";
import AdminLogin from "../view/AdminLogin";
import AdminHome from "../view/AdminHome";
import AdminSchool from "../components/AdminSchool";
import AdminChangePassword from "../components/AdminChangePassword";
import AdminBaseInformation from "../components/AdminBaseInformation";
import OneDay from "../components/OneDay";
import StudentOneDay from "../components/StudentOneDay";
import ThreeMeals from "../components/ThreeMeals";
import ThreeMealsTotal from "../components/ThreeMealsTotal";
import OneDayTotal from "../components/OneDayTotal";
import Analysis from "../components/Analysis";
import CollegeMonthCS from "../components/CollegeMonthCS";
import CollegeDayCS from "../components/CollegeDayCS";
import CollegeDayTCS from "../components/CollegeDayTCS";
import CollegeMonthTCS from "../components/CollegeMonthTCS";
import MajorDayTCS from "../components/MajorDayTCS";
import MajorDayCS from "../components/MajorDayCS";
import MajorMonthTCS from "../components/MajorMonthTCS";
import MajorMonthCS from "../components/MajorMonthCS";

Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '/',
      redirect: {name: 'SchoolLogin'}
    }, {
      path: '/schoolLogin',
      name: 'SchoolLogin',
      component: SchoolLogin
    }, {
      path: '/schoolHome',
      component: SchoolHome,
      name: '首页',
      children: [
        {
          path: '/changePassword',
          component: ChangePassword,
          name: '修改密码'
        }, {
          path: '/changeName',
          component: ChangeName,
          name: '修改昵称',
        }, {
          path: '/oneDay',
          component: OneDay,
          name: "一日消费分析"
        }, {
          path: '/studentOneDay',
          component: StudentOneDay,
          name: "学生日(月)消费分析"
        }, {
          path: '/threeMeals',
          component: ThreeMeals,
          name: '三餐消费分析'
        }, {
          path: '/threeMealsTotal',
          component: ThreeMealsTotal,
          name: '三餐消费总和统计分析'
        }, {
          path: '/oneDayTotal',
          component: OneDayTotal,
          name: '消费总和统计分析'
        }, {
          path: '/analysis',
          component: Analysis,
          name: '贫困生分析'
        }, {
          path: '/collegeMonthCS',
          component: CollegeMonthCS,
          name: '学院每月分析'
        }, {
          path: '/collegeDayCS',
          component: CollegeDayCS,
          name: '学院每日分析'
        }, {
          path: '/collegeDayTCS',
          component: CollegeDayTCS,
          name: '学院每日三餐分析'
        },{
          path: '/collegeMonthTCS',
          component: CollegeMonthTCS,
          name: '学院每月三餐分析'
        }, {
          path: '/majorMonthCS',
          component: MajorMonthCS,
          name: '专业每月分析'
        }, {
          path: '/majorDayCS',
          component: MajorDayCS,
          name: '专业每日分析'
        }, {
          path: '/majorDayTCS',
          component: MajorDayTCS,
          name: '专业每日三餐分析'
        },{
          path: '/majorMonthTCS',
          component: MajorMonthTCS,
          name: '专业每月三餐分析'
        }
      ]
    }, {
      path: '/adminLogin',
      name: 'AdminLogin',
      component: AdminLogin
    }, {
      path: '/adminHome',
      name: '管理员首页',
      component: AdminHome,
      children: [{
        path: '/school',
        name: '学校管理员信息管理',
        component: AdminSchool,
      }, {
        path: '/adminChangePassword',
        name: '管理员修改密码',
        component: AdminChangePassword,
      }, {
        path: '/adminBaseInformation',
        name: '基本信息',
        component: AdminBaseInformation
      }]
    },
  ]
});
router.beforeEach((to, from, next) => {
  if (to.path === '/' || to.path === '/schoolLogin' || to.path === '/adminLogin') {
    next();
  } else {
    const tokenStr = localStorage.getItem('token')
    if (!tokenStr) {
      next('/')
    } else {
      next();
    }
  }
})
export default router;

