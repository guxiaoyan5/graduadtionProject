import Vue from 'vue'
import Router from 'vue-router'
import SchoolLogin from "../view/school/Login";
import SchoolHome from "../view/school/Test";
//import Cookies from 'js-cookie';
Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '/',
      //component: SchoolLogin,
      redirect: {name: 'SchoolLogin'}
    }, {
      path: '/schoolLogin',
      name: 'SchoolLogin',
      component: SchoolLogin
    }, {
      path: '/schoolHome',
      component: SchoolHome,
      name: 'SchoolHome',
      children: []
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

