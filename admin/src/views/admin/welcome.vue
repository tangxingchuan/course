<template>
    <div>
        <h1>你好，欢迎您进入控制台</h1>
    </div>
</template>

<script>
    export default {
        name: "welcome",
        mounted() {
          //this.$parent.activeSidebar('welcome-sidebar')  //在admin页面通过watch监听路由路径，做了一个通用的sidebar菜单激活，解决了这个问题
        },
        methods:{

        },

    }
</script>

<style scoped>

</style>
