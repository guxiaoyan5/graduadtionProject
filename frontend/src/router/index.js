import Vue from 'vue'
import Router from 'vue-router'
import SchoolLogin from "../view/school/Login";
import SchoolHome from "../view/school/Home";
import Header from "../components/Header";
import ChangePassword from "../components/ChangePassword";
import ChangeName from "../components/ChangeName";
//import Cookies from 'js-cookie';
Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '/',
      // component: SchoolHome,
      redirect: {name: 'SchoolLogin'}
    }, {
      path: '/schoolLogin',
      name: 'SchoolLogin',
      component: SchoolLogin
    }, {
      path: '/schoolHome',
      component: SchoolHome,
      name: 'SchoolHome',
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
    }
  ]
});
router.beforeEach((to, from, next) => {
  if (to.path === '/' || to.path === '/schoolLogin') {
    next();
  } else {
    const tokenStr = localStorage.getItem('token')
    if (!tokenStr) {
      next('/schoolLogin')
    } else {
      next();
    }
  }
})
export default router;

