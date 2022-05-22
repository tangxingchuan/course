<template>
    <div>
        <h1>你好，欢迎您进入控制台</h1>
    </div>
</template>

<script>
    export default {
        name: "welcome",
        mounted() {
          this.$parent.activeSidebar('welcome-sidebar')
        },
        methods:{

        },

    }
</script>

<style scoped>

</style>
