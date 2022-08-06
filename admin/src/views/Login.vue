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
                                                                   placeholder="用户名" v-model=" userCourse.loginName"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                                                    </label>

                                                    <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control"
                                                                 v-model="userCourse.password"  placeholder="密码"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                                    </label>

                                                    <div class="space"></div>

                                                    <div class="clearfix">
                                                        <label class="inline">
                                                            <input type="checkbox" v-model="remember" class="ace"/>
                                                            <span class="lbl"> 记住账号</span>
                                                        </label>

                                                        <button type="button"
                                                                class="width-35 pull-right btn btn-sm btn-primary">
                                                            <i class="ace-icon fa fa-key"></i>
                                                            <span class="bigger-110" @click="login">登录</span>
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
    //$('body').attr('class', 'login-layout light-login');

    //$('body').attr('class', 'login-layout blur-login');
    import axios from "axios";

    export default {
        name: "login",
        data(){
          return{
              userCourse: {},
              remember:true,
          }
          },
        mounted: function () {
            $('body').removeClass('no-skin');
            $('body').attr('class', 'login-layout light-login');

            //从缓存中，获取用户名和密码，如果没有，说明上一次没勾选"记住我"
                       let rememberUser  = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
                       if (rememberUser){
                           this.userCourse = rememberUser
                       }



        },

        methods:{
            login(){

                let passwordShow = this.userCourse.password;
                this.userCourse.password = hex_md5(this.userCourse.password + KEY);
                Loading.show();
                axios.post(process.env.VUE_APP_SERVER + '/system/admin/userCourse/login',  this.userCourse).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    let loginUser = resp.content;
                    if (resp.success) {
                        console.log("登录成功 ：",resp.content);
                        Tool.setLoginUser(resp.content);
                        if (this.remember){
                            LocalStorage.set(LOCAL_KEY_REMEMBER_USER,{
                                loginName: loginUser.loginName,
                                password: passwordShow
                            })
                        }else {
                            LocalStorage.set(LOCAL_KEY_REMEMBER_USER,null)
                        }

                        this.$router.push('/welcome')
                    } else {
                        Toast.warning(resp.message)
                    }
                })
            },
        },

    }
</script>

<style scoped>

</style>
