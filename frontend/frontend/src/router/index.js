import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ElementUi from 'element-ui'
import echarts from 'echarts'

Vue.use(Router)
Vue.use(ElementUi)
Vue.prototype.$echarts = echarts
export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',

      component: HelloWorld
    }, {
      path: '/login',
      name: 'login',

    }
  ]
})
