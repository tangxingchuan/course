import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/login";
import Admin from "@/views/admin";
import Welcome from "@/views/admin/welcome";
import Course from "@/views/admin/course";
import Chapter from "@/views/admin/chapter";
import Section from "@/views/admin/section";
import Category from "@/views/admin/category";
import Teacher from "@/views/admin/teacher";
import File from "@/views/admin/file";

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
                name: 'business/category',
                path: 'business/category',
                component:Category,
            },
            {
                name: 'business/course',
                path: 'business/course',
                component:Course,
            },
            {
                name: 'business/chapter',
                path: 'business/chapter',
                component:Chapter,
            },
            {
                name: 'business/section',
                path: 'business/section',
                component:Section,
            },
            {
                name: 'business/teacher',
                path: 'business/teacher',
                component:Teacher,
            },
            {
                name: 'file/file',
                path: 'file/file',
                component:File,
            },

            ]
    }
]

const router = new VueRouter({
    mode:'history',
    base:process.env.BASE_URL,
    routes
})

export default router
