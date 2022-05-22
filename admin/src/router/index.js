import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/login";
import Admin from "@/views/admin";
import Welcome from "@/views/admin/welcome";
import Chapter from "@/views/admin/chapter";

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
        path: '/',
        name:'admin',
        component:Admin,
        children:[
            {
                name:'welcome',
                path: 'welcome',
                component:Welcome,
            },
            {
                name: 'business/chapter',
                path: 'business/chapter',
                component:Chapter,
            }
            ]
    }
]

const router = new VueRouter({
    mode:'history',
    base:process.env.BASE_URL,
    routes
})

export default router
