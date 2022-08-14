import Vue from 'vue'
import App from './App.vue'
import router from "@/router/router";
import axios from "axios";
import filter from "@/filter/filter";

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;



// 全局过滤器
Object.keys(filter).forEach(key => {
    Vue.filter(key, filter[key])
});

new Vue({
  render: h => h(App),
    router,
}).$mount('#app')
