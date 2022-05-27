import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from "axios";


Vue.config.productionTip = false
Vue.use(axios);


axios.interceptors.request.use(function (config) {
    console.log('请求：',config);
    return config;
},error => {});
axios.interceptors.response.use(function (response) {
    console.log('返回结果：',response);
    return response;
},error => {});



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
