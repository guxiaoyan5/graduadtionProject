// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import axios from "axios";
// import echarts from 'echarts'
import Vuex from 'vuex'
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Vuex)
Vue.prototype.$axios = axios
// Vue.prototype.$echarts = echarts
/* eslint-disable no-new */
import store from "./store";
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('token')) {
      config.headers.Authorization = localStorage.getItem('token');
    }
    return config;
  }, error => {
    return Promise.reject(error);
  });

new Vue({
  el: '#app',
  router,
  store:store,
  components: { App },
  template: '<App/>'
})
