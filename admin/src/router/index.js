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
import Content from "@/views/admin/content";
import UserCourse from "@/views/admin/userCourse";
import Resource from "@/views/admin/resource";
import Role from "@/views/admin/role";

Vue.use(VueRouter);


export default new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [
        {
        path: "*",
        redirect: "/login",
    }, {
        path: "",
        redirect: "/login",
    },
        {
        path: "/login",
        component: Login
    }, {
        path: "/",
        name: "admin",
        component: Admin,
        // meta: {
        //     loginRequire: true
        // },
        children: [{
            path: "welcome",
            name: "welcome",
            component: Welcome,
        }, {
            path: "business/category",
            name: "business/category",
            component: Category,
        }, {
            path: "business/course",
            name: "business/course",
            component: Course,
        }, {
            path: "business/chapter",
            name: "business/chapter",
            component: Chapter,
        }, {
            path: "business/section",
            name: "business/section",
            component: Section,
        }, {
            path: "business/content",
            name: "business/content",
            component: Content,
        }, {
            path: "business/teacher",
            name: "business/teacher",
            component: Teacher,
        }, {
            path: "file/file",
            name: "file/file",
            component: File,
        }, {
            path: "system/userCourse",
            name: "system/userCourse",
            component: UserCourse,
        }, {
            path: "system/resource",
            name: "system/resource",
            component: Resource,
        }, {
            path: "system/role",
            name: "system/role",
            component: Role,
        }]
    }]
})
