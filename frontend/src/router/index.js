import Vue from 'vue'
import Router from 'vue-router'
import SchoolLogin from "../view/school/Login";
import SchoolHome from "../view/school/Home";
import ChangePassword from "../components/ChangePassword";
import ChangeName from "../components/ChangeName";
import AdminLogin from "../view/AdminLogin";
import AdminHome from "../view/AdminHome";
import AdminConsume from "../components/AdminConsume";
import AdminStudent from "../components/AdminStudent";
import AdminStore from "../components/AdminStore";
import AdminSchool from "../components/AdminSchool";
import AdminChangePassword from "../components/AdminChangePassword";

Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '/',
      redirect: {name: 'AdminLogin'}
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
        path: '/student',
        name: '学生管理',
        component: AdminStudent,
      }, {
        path: '/store',
        name: '商户管理',
        component: AdminStore
      }, {
        path: '/consume',
        name: '消费管理',
        component: AdminConsume
      },{
        path : '/school',
        name : '学校管理员信息管理',
        component: AdminSchool,
      },{
        path : '/adminChangePassword',
        name : '修改密码',
        component: AdminChangePassword,
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

