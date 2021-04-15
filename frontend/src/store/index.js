import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
  },
  mutations: {
    setToken (state,token) {
      state.token =token.token;
      localStorage.setItem("token",token.token);     //存储token
    }
  }
})
export default store;
