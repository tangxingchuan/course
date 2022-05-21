<template>
    <div>
        <div class="main-container">
            <div class="main-content">
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1">
                        <div class="login-container">
                            <div class="center">
                                <h1>
                                    <i class="ace-icon fa fa-leaf green"></i>
                                    <span class="red">左眼</span>
                                    <span class="white" id="id-text2">后台系统</span>
                                </h1>
                                <h4 class="blue" id="id-company-text">&copy; 艾欧尼亚</h4>
                            </div>

                            <div class="space-6"></div>

                            <div class="position-relative">
                                <div id="login-box" class="login-box visible widget-box no-border">
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <h4 class="header blue lighter bigger">
                                                <i class="ace-icon fa fa-coffee green"></i>
                                                请输入您的信息
                                            </h4>

                                            <div class="space-6"></div>

                                            <form>
                                                <fieldset>
                                                    <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control"
                                                                   placeholder="Username" v-model="from.Username"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                                                    </label>

                                                    <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control"
                                                                   placeholder="Password" v-model.number="from.Password"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                                    </label>

                                                    <div class="space"></div>

                                                    <div class="clearfix">
                                                        <label class="inline">
                                                            <input type="checkbox" class="ace"/>
                                                            <span class="lbl"> 记住账号</span>
                                                        </label>

                                                        <button type="button"
                                                                class="width-35 pull-right btn btn-sm btn-primary">
                                                            <i class="ace-icon fa fa-key"></i>
                                                            <span class="bigger-110" @click="userPwd">登录</span>
                                                        </button>
                                                    </div>

                                                    <div class="space-4"></div>
                                                </fieldset>
                                            </form>

                                        </div><!-- /.widget-main -->

                                    </div><!-- /.widget-body -->
                                </div><!-- /.login-box -->


                            </div><!-- /.position-relative -->

                        </div>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.main-content -->
        </div>
    </div>

</template>

<script>
    import Admin from "./admin";

    $('body').attr('class', 'login-layout blur-login');
    export default {
        name: "login",
        data(){
          return{
             from: {
                 Username:'',
                 Password:'',
             }


          }
        },
        methods:{
            userPwd(from){
                console.log('启动函数')
                if(from.Password=== 1234 && from.Username==='test'){
                    console.log('进入if判断')
                    this.$router.push('/admin')
                    alert('登陆成功')
                }else {
                    alert('登录失败！')
                }
            }
        }
    }
</script>

<style scoped>

</style>
