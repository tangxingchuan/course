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

                                                    <label class="block clearfix">
														<span class="block input-icon input-icon-right">
                                                           <div class="input-group">
                                                            <input type="text"  v-model="userCourse.imageCode" class="form-control" placeholder="验证码" >
                                                            <span class="input-group-addon" id="basic-addon2">
                                                            <img @click="loadImageCode()" id="image-code"  alt="验证码">
                                                            </span>
                                                            </div>
														</span>
                                                    </label>



                                                    <div class="input-group">

                                                    </div>

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
              remember: true, // 默认勾选记住我
              imageCodeToken: ""
          }
          },
        mounted: function () {
            $('body').removeClass('no-skin');
            $('body').attr('class', 'login-layout light-login');

            this.userCourse.imageCodeToken = this.imageCodeToken;
            //从缓存中，获取用户名和密码，如果没有，说明上一次没勾选"记住我"
                       let rememberUser  = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
                       if (rememberUser){
                           this.userCourse = rememberUser
                       }



        },



        methods:{
            login(){

                // 将明文存储到缓存中
                //let passwordShow = this.userCourse.password;


                // 如果密码是从缓存带出来的，则不需要重新加密
                    let md5 =  hex_md5(this.userCourse.password);
                    let rememberUser  = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
                    if (md5 !== rememberUser.md5){

                        this.userCourse.password = hex_md5(this.userCourse.password + KEY);
                    }

                this.userCourse.imageCodeToken = this.imageCodeToken;
                Loading.show();
                axios.post(process.env.VUE_APP_SERVER + '/system/admin/userCourse/login',  this.userCourse).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    let loginUser = resp.content;
                    if (resp.success) {
                        console.log("登录成功 ：",resp.content);
                        Tool.setLoginUser(resp.content);
                        //判断"记住我"
                        if (this.remember){

                            // 如果勾选记住我，则将用户名密码保存到本地缓存
                            // 原：这里需要保存密码明文，否则登录时又会再加一层密
                            // 新：这里保存密码密文，并保存密文md5，用于检测密码是否被重新输入过
                            let md5 = hex_md5(this.userCourse.password);
                            LocalStorage.set(LOCAL_KEY_REMEMBER_USER,{
                                loginName: loginUser.loginName,
                                password: this.userCourse.password,
                                md5: md5
                            });
                        }else {
                            //如果没有勾选"记住我"，要把本地缓存清空，否则按照mounted的逻辑，下次打开时会自动显示用户名和密码
                            LocalStorage.set(LOCAL_KEY_REMEMBER_USER,null)
                        }

                        this.$router.push('/welcome')
                    } else {
                        Toast.warning(resp.message);
                        this.userCourse.password='';
                        this.loadImageCode();
                    }
                })
            },

            loadImageCode(){

                     this.imageCodeToken = Tool.uuid(8);
                     $('#image-code').attr('src',process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/'+this.imageCodeToken);
            }
        },

    }
</script>

<style scoped>
    .input-group-addon{
        padding: 0;
    }

</style>
