import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/login";
import Admin from "@/views/admin";

Vue.use(VueRouter)

const routes = [
  {
    path: '*',
    redirect:'/login'
  },
    {
        path: '/login',
        component:Login,
    },
    {
        name:'tang',
        path: '/admin',
        component:Admin,
    }
]

const router = new VueRouter({
    mode:'history',
    base:process.env.BASE_URL,
    routes
})

export default router
