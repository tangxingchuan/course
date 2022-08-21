import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/views/index";
import List from "@/views/list";
import Detail from "@/views/detail";


Vue.use(VueRouter)

export  default  new  VueRouter({
    mode:'history',
    base:process.env.BASE_URL,
    routes:[{
        path:'*',
        redirect:"/index",

    },{
        path:'/index',
        component:Index
    },{
        path:'/list',
        component:List
    },{
        path:"/detail",
        component:Detail
    }
    ]

})
