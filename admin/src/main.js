import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from "axios";
import filter from "@/filter/filter";

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;


/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
    console.log('请求：',config);
    return config;
},error => {});
axios.interceptors.response.use(function (response) {
    console.log('返回结果：',response);
    return response;
},error => {});


//全局过滤
Object.keys(filter).forEach(key=>{
    let f = Vue.filter(key,filter[key]);
    console.log("全局过滤",f);
});


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


console.log('环境 :'+process.env.NODE_ENV)
