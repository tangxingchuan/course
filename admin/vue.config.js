const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
    lintOnSave:false,  //设置是否在开发环境下每次都启用eslint验证
})
