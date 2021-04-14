import Vue from 'vue'
import Router from 'vue-router'
import SchoolLogin from "../view/school/Login";
import SchoolHome from "../view/school/Home";
//import echarts from 'echarts'
//import Cookies from 'js-cookie';
Vue.use(Router)
//Vue.prototype.$echarts = echarts
export default new Router({
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
      path: '/home',
      component: SchoolHome
    }
  ]
})
