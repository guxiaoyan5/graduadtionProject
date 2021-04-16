import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    token: localStorage.getItem('token') ? localStorage.getItem('token') : '',
    id: localStorage.getItem('id') ? localStorage.getItem('id') : '',
    name: localStorage.getItem('name') ? localStorage.getItem('name') : ''
  },
  mutations: {
    setToken(state, token) {
      state.token = token.token;
      localStorage.setItem("token", token.token);     //存储token
    },
    setSchoolUser(state, token) {
      state.id = token.id
      localStorage.setItem("id", token.id);
      state.name = token.name
      localStorage.setItem("name", token.name);
    }
  },
  getters:{
    getSchoolUser(state){
      if(localStorage.getItem('name')){
        state.name=localStorage.getItem('name')
      }
      return state.name
    }
  }
})
export default store;
