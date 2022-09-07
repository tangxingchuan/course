const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
    lintOnSave:false /*关闭语法检查*/,
    publicPath: process.env.NODE_ENV === 'production'
    ?'./'
    :'/'

})
