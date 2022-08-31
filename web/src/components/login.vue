<template>
    <div id="login-modal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-login" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="login-div" v-show="MODAL_STATUS === STATUS_LOGIN">
                        <h3>登&nbsp;&nbsp;录</h3>
                        <div class="form-group">
                            <input v-model="member.mobile" class="form-control" placeholder="手机号">
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="password" placeholder="密码" v-model="member.password">
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <input id="image-code-input" class="form-control" type="text" placeholder="验证码"
                                       v-model="member.imageCode">
                                <div class="input-group-addon" id="image-code-addon">
                                    <img id="image-code" alt="验证码" v-on:click="loadImageCode()"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <button v-on:click="login()" class="btn btn-primary btn-block submit-button">
                                登&nbsp;&nbsp;录
                            </button>
                        </div>
                        <div class="form-group">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" class="remember" v-model="remember"> 记住密码
                                </label>
                                <div class="pull-right">
                                    <a href="javascript:;" v-on:click="toForgetDiv()">忘记密码</a>&nbsp;
                                    <a href="javascript:;" v-on:click="toRegisterDiv()">我要注册</a>
                                </div>
                            </div>
                        </div>
                        <div class="form-group to-register-div">
                        </div>
                    </div>
                    <div class="register-div" v-show="MODAL_STATUS === STATUS_REGISTER">
                        <h3>注&nbsp;&nbsp;册</h3>
                        <div class="form-group">
                            <input v-on:blur="onRegisterMobileBlur()"
                                   v-bind:class="registerMobileValidateClass"
                                id="register-mobile" v-model="memberRegister.mobile"
                                   class="form-control" placeholder="手机号">
                            <span v-show="registerMobileValidate  === false" class="text-danger">手机号11位数，且不能重复</span>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <input  v-on:blur="onRegisterMobileCodeBlur()"
                                        v-bind:class="registerMobileCodeValidateClass"
                                    id="register-mobile-code" class="form-control"
                                       placeholder="手机验证码" v-model="memberRegister.smsCode">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" id="register-send-code-btn"
                                            v-on:click="sendSmsForRegister()">发送验证码
                                    </button>
                                </div>
                            </div>
                            <span v-show="registerMobileCodeValidate === false" class="text-danger">请输入短信6位验证码</span>
                        </div>
                        <div class="form-group">
                            <input v-on:blur="onRegisterNameBlur()"
                                   v-bind:class="registerNameValidateClass"
                                id="register-name" v-model="memberRegister.name"
                                   class="form-control" placeholder="昵称">

                            <span v-show="registerNameValidate === false" class="text-danger">昵称2到20位中文，字母，数字，下划线组合</span>
                        </div>
                        <div class="form-group">
                            <input   v-on:blur="onRegisterPasswordBlur()"
                                     v-bind:class="registerPasswordValidateClass"
                                id="register-password" v-model="memberRegister.passwordOriginal"
                                   class="form-control" placeholder="密码" type="password">
                            <span v-show="registerPasswordValidate === false" class="text-danger">密码最少6位，包含至少1字母和1个数字</span>
                        </div>
                        <div class="form-group">
                            <input v-on:blur="onRegisterConfirmPasswordBlur()"
                                   v-bind:class="registerConfirmPasswordValidateClass"
                                id="register-confirm-password" v-model="memberRegister.confirm"
                                   class="form-control" placeholder="确认密码"
                                   name="memberRegisterConfirm" type="password">
                            <span v-show="registerConfirmPasswordValidate === false" class="text-danger">确认密码和密码一致</span>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary btn-block submit-button" v-on:click="register()">
                                注&nbsp;&nbsp;册
                            </button>
                        </div>
                        <div class="form-group to-login-div">
                            <a href="javascript:;" v-on:click="toLoginDiv()">我要登录</a>
                        </div>
                    </div>
                    <div class="forget-div" v-show="MODAL_STATUS === STATUS_FORGET">
                        <h3>忘记密码</h3>
                        <div class="form-group">
                            <input v-on:blur="onForgetMobileBlur()"
                                   v-bind:class="forgetMobileValidateClass"
                                   id="forget-mobile" v-model="memberForget.mobile"
                                   class="form-control" placeholder="手机号">
                            <span v-show="forgetMobileValidate === false" class="text-danger">手机号11位数字，且必须已注册</span>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <input v-on:blur="onForgetMobileCodeBlur()"
                                       v-bind:class="forgetMobileCodeValidateClass"
                                       id="forget-mobile-code" class="form-control"
                                       placeholder="手机验证码" v-model="memberForget.smsCode">
                                <div class="input-group-append">
                                    <button v-on:click="sendSmsForForget()"
                                            class="btn btn-outline-secondary" id="forget-send-code-btn">
                                        发送验证码
                                    </button>
                                </div>
                            </div>
                            <span v-show="forgetMobileCodeValidate === false" class="text-danger">请输入短信6位验证码</span>
                        </div>
                        <div class="form-group">
                            <input v-on:blur="onForgetPasswordBlur()"
                                   v-bind:class="forgetPasswordValidateClass"
                                   id="forget-password" v-model="memberForget.passwordOriginal"
                                   class="form-control" placeholder="密码" type="password">
                            <span v-show="forgetPasswordValidate === false" class="text-danger">密码最少6位，包含至少1字母和1个数字</span>
                        </div>
                        <div class="form-group">
                            <input v-on:blur="onForgetConfirmPasswordBlur()"
                                   v-bind:class="forgetConfirmPasswordValidateClass"
                                   id="forget-confirm-password" v-model="memberForget.confirm"
                                   class="form-control" placeholder="确认密码" type="password">
                            <span v-show="forgetConfirmPasswordValidate === false" class="text-danger">确认密码和密码一致</span>
                        </div>
                        <div class="form-group">
                            <button v-on:click="resetPassword()" class="btn btn-primary btn-block submit-button">
                                重&nbsp;&nbsp;置
                            </button>
                        </div>
                        <div class="form-group to-login-div">
                            <a href="javascript:;" v-on:click="toLoginDiv()">我要登录</a>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</template>

<script>
    import axios from "axios";
    export default {
        name: 'the-login',
        data: function () {
            return {

                // 模态框内容切换：登录、注册、忘记密码
                STATUS_LOGIN: "STATUS_LOGIN",
                STATUS_REGISTER: "STATUS_REGISTER",
                STATUS_FORGET: "STATUS_FORGET",
                MODAL_STATUS: "",

                member: {},
                memberForget: {},
                memberRegister: {},

                remember: true, // 记住密码
                imageCodeToken: "",

                //注册框显示错误信息
                registerMobileValidate:'',
                registerMobileCodeValidate:'',
                registerNameValidate:'',
                registerPasswordValidate:'',
                registerConfirmPasswordValidate:'',


                // 忘记密码框显示错误信息
                forgetMobileValidate: null,
                forgetMobileCodeValidate: null,
                forgetPasswordValidate: null,
                forgetConfirmPasswordValidate: null,

            }
        },

        computed:{
            //---------------------------注册的计算属性--------------------

            registerMobileValidateClass:function () {
                return{
                    "border_success" : this.registerMobileValidate ===true,
                    "border_danger" : this.registerMobileValidate ===false,

                }
            },

            registerMobileCodeValidateClass:function () {
                return{
                    "border_success" : this.registerMobileCodeValidate ===true,
                    "border_danger" : this.registerMobileCodeValidate ===false,

                }
            },

            registerNameValidateClass:function () {
                return{
                    "border_success" : this.registerNameValidate ===true,
                    "border_danger" : this.registerNameValidate ===false,

                }
            },
            registerPasswordValidateClass:function () {
                return{
                    "border_success" : this.registerPasswordValidate ===true,
                    "border_danger" : this.registerPasswordValidate ===false,

                }
            },
            registerConfirmPasswordValidateClass:function () {
                return{
                    "border_success" : this.registerConfirmPasswordValidate ===true,
                    "border_danger" : this.registerConfirmPasswordValidate ===false,

                }
            },


            //---------------------------忘记密码的计算属性--------------------

            forgetMobileValidateClass: function () {
                return {
                    'border-success': this.forgetMobileValidate === true,
                    'border-danger': this.forgetMobileValidate === false,
                }
            },
            forgetMobileCodeValidateClass: function () {
                return {
                    'border-success': this.forgetMobileCodeValidate === true,
                    'border-danger': this.forgetMobileCodeValidate === false,
                }
            },
            forgetPasswordValidateClass: function () {
                return {
                    'border-success': this.forgetPasswordValidate === true,
                    'border-danger': this.forgetPasswordValidate === false,
                }
            },
            forgetConfirmPasswordValidateClass: function () {
                return {
                    'border-success': this.forgetConfirmPasswordValidate === true,
                    'border-danger': this.forgetConfirmPasswordValidate === false,
                }
            },
        },

        mounted() {

            this.toLoginDiv();
        },
        methods: {

            //---------------登录框注册校验-----------------

            //手机号校验
            onRegisterMobileBlur: function () {

                this.registerMobileValidate  = Pattern.validateMobile(this.memberRegister.mobile);
                return this.registerMobileValidate;
            },

            //验证码校验
            onRegisterMobileCodeBlur: function () {

                this.registerMobileCodeValidate  = Pattern.validateMobileCode(this.memberRegister.smsCode);
                return this.registerMobileValidate;
            },

            //昵称校验
            onRegisterNameBlur: function () {

                this.registerNameValidate  = Pattern.validateName(this.memberRegister.name);
                return this.registerMobileValidate;
            },

            //密码校验
            onRegisterPasswordBlur () {

                this.registerPasswordValidate = Pattern.validatePasswordWeak(this.memberRegister.passwordOriginal);
                return this.registerMobileValidate;
            },

            //密码再次校验
            onRegisterConfirmPasswordBlur: function () {

                let confirmPassword  = $("#register-confirm-password").val();
                if (Tool.isEmpty(confirmPassword) ){

                    return this.registerConfirmPasswordValidate = false;
                }

                return this.registerConfirmPasswordValidate = (confirmPassword === this.memberRegister.passwordOriginal);

            },


            //-------------------------------- 忘记密码框校验 ----------------------------

            onForgetMobileBlur () {

                return this.forgetMobileValidate = Pattern.validateMobile(this.memberForget.mobile);
            },

            onForgetMobileCodeBlur () {

                return this.forgetMobileCodeValidate = Pattern.validateMobileCode(this.memberForget.smsCode);
            },

            onForgetPasswordBlur () {

                return this.forgetPasswordValidate = Pattern.validatePasswordWeak(this.memberForget.passwordOriginal);
            },

            onForgetConfirmPasswordBlur () {

                let forgetPassword = $("#forget-confirm-password").val();
                if (Tool.isEmpty(forgetPassword)) {
                    return this.forgetConfirmPasswordValidate = false;
                }
                return this.forgetConfirmPasswordValidate = (forgetPassword === this.memberForget.passwordOriginal);
            },


            /**
             * 打开登录注册窗口
             */
            openLoginModal() {
                $("#login-modal").modal("show");
            },

            //---------------登录框、注册框、忘记密码框切换-----------------
            toLoginDiv() {

                // 从缓存中获取记住的用户名密码，如果获取不到，说明上一次没有勾选“记住我”
                let rememberMember =LocalStorage.get(LOCAL_KEY_REMEMBER_MEMBER);
                if (rememberMember ){
                    this.member =rememberMember;
                }

                //显示登录框时就刷新一次验证码图片
                this.loadImageCode();

                this.MODAL_STATUS = this.STATUS_LOGIN
            },
            toRegisterDiv() {

                this.MODAL_STATUS = this.STATUS_REGISTER
            },
            toForgetDiv() {

                this.MODAL_STATUS = this.STATUS_FORGET
            },

            register() {

                // 提交之前，先校验所有输入框
                // 注意：当有一个文本框校验为false时，其它不校验
                // let validateResult = this.onRegisterMobileBlur() &&
                //     this.onRegisterMobileCodeBlur() &&
                //     this.onRegisterNameBlur() &&
                //     this.onRegisterPasswordBlur() &&
                //     this.onRegisterConfirmPasswordBlur();
                // if (!validateResult) {
                //     return;
                // }

                let validateResult1 = this.onRegisterMobileBlur();
                let validateResult2 = this.onRegisterMobileCodeBlur();
                let validateResult3 = this.onRegisterNameBlur() ;
                let validateResult4 = this.onRegisterPasswordBlur();
                let validateResult5 = this.onRegisterConfirmPasswordBlur();

                let validateResult = validateResult1 &&
                    validateResult2&&
                    validateResult3&&
                    validateResult4&&
                    validateResult5;

                if (!validateResult) {
                          return;
                }


                this.memberRegister.password = hex_md5(this.memberRegister.passwordOriginal + KEY);

                // 调服务端注册接口
                axios.post(process.env.VUE_APP_SERVER + '/business/web/member/register', this.memberRegister).then((response) => {
                    let resp = response.data;
                    if (resp.success) {
                        Toast.success("注册成功");
                    } else {
                        Toast.warning(resp.message);
                    }
                })
            },

            //------------------------登录框-------------------


            login(){

                // 如果密码是从缓存带出来的，则不需要重新加密
                let md5 =  hex_md5(this.member.password);
                let rememberUser  = LocalStorage.get(LOCAL_KEY_REMEMBER_MEMBER) || {};
                if (md5 !== rememberUser.md5){

                    this.member.password = hex_md5(this.member.password + KEY);
                }

                this.member.imageCodeToken = this.imageCodeToken;

                axios.post(process.env.VUE_APP_SERVER + '/business/web/member/login',  this.member).then((response)=>{

                    let resp = response.data;
                    if (resp.success) {
                        console.log("登录成功 ：",resp.content);
                        let loginMember = resp.content;

                        Tool.setLoginMember(resp.content);
                        //判断"记住我"
                        if (this.remember){

                            // 如果勾选记住我，则将用户名密码保存到本地缓存
                            // 原：这里需要保存密码明文，否则登录时又会再加一层密
                            // 新：这里保存密码密文，并保存密文md5，用于检测密码是否被重新输入过
                            let md5 = hex_md5(this.member.password);
                            LocalStorage.set(LOCAL_KEY_REMEMBER_MEMBER,{
                                mobile: loginMember.mobile,
                                password: this.member.password,
                                md5: md5
                            });
                        }else {
                            //如果没有勾选"记住我"，要把本地缓存清空，否则按照mounted的逻辑，下次打开时会自动显示用户名和密码
                            LocalStorage.set(LOCAL_KEY_REMEMBER_MEMBER,null)
                        }


                       //登录成功
                        this.$parent.setLoginMember(loginMember);
                        $("#login-modal").modal("hide");

                    } else {
                        Toast.warning(resp.message);
                        this.member.password='';
                        this.loadImageCode();
                    }
                })
            },



            //------------------------注册短信验证-------------------
            /**
             * 发送注册短信
             */
            sendSmsForRegister() {


                if(!this.onRegisterMobileBlur()){
                    return false;
                }

                let sms = {
                    mobile: this.memberRegister.mobile,
                    use: SMS_USE.REGISTER.key
                };

                axios.get(process.env.VUE_APP_SERVER + '/business/web/member/is-mobile-exist/' + this.memberRegister.mobile).then((res)=> {
                    let response = res.data;

                    if (response.success){

                        Toast.warning("手机号已经被注册")

                    }else {

                        //调用服务端去发送短信
                        this.sendSmsCode(sms, "register-send-code-btn");
                    }


                })


            },

            /**
             * 发送短信
             */
            sendSmsCode(sms, btnId) {

                // 调服务端发短信接口
                axios.post(process.env.VUE_APP_SERVER + '/business/web/sms/send', sms).then((res)=> {
                    let response = res.data;
                    if (response.success) {
                        Toast.success("短信已发送")

                        // 开始倒计时
                        this.countdown = 60;
                        this.setTime(btnId)
                    } else {
                        Toast.warning(response.message);
                    }
                })
            },


            /**
             * 倒计时
             * @param btnId
             */
            setTime(btnId) {
                let btn = $("#" + btnId);
                if (this.countdown === 0) {
                    btn.removeAttr("disabled");
                    btn.text("获取验证码");
                    return;
                } else {
                    btn.attr("disabled", true);
                    btn.text("重新发送(" + this.countdown + ")");
                    this.countdown--;
                }

                setTimeout(function () {
                    this.setTime(btnId)
                }, 1000);
            },

            /**
             * 发送忘记密码短信
             */
            sendSmsForForget() {
                if (!this.onForgetMobileBlur()) {
                    return false;
                }
                let sms = {
                    mobile: this.memberForget.mobile,
                    use: SMS_USE.FORGET.key
                };

                axios.get(process.env.VUE_APP_SERVER + '/business/web/member/is-mobile-exist/' + this.memberForget.mobile).then((res)=>{
                    let response = res.data;
                    if (response.success) {
                        this.forgetMobileValidate = true;
                        this.sendSmsCode(sms, "forget-send-code-btn");
                    } else {
                        this.forgetMobileValidate = false;
                        Toast.warning("手机号未注册");
                    }
                });
            },

            resetPassword() {

                // 提交之前，先校验所有输入框
                // 注意：当有一个文本框校验为false时，其它不校验
                let validateResult = this.onForgetMobileBlur() &&
                    this.onForgetMobileCodeBlur() &&
                    this.onForgetPasswordBlur() &&
                    this.onForgetConfirmPasswordBlur();
                if (!validateResult) {
                    return;
                }

                this.memberForget.password = hex_md5(this.memberForget.passwordOriginal + KEY);

                // 调服务端密码重置接口
                axios.post(process.env.VUE_APP_SERVER + '/business/web/member/reset-password', this.memberForget).then((res)=>{
                    let response = res.data;
                    if (response.success) {
                        Toast.success("密码重置成功");
                        this.toLoginDiv();
                    } else {
                        Toast.warning(response.message);
                    }
                }).catch((response)=>{
                    console.log("error：", response);
                })
            },

            /**
             * 加载图形验证码
             */
            loadImageCode(){

                this.imageCodeToken = Tool.uuid(8);
                $('#image-code').attr('src',process.env.VUE_APP_SERVER + '/business/web/kaptcha/image-code/'+ this.imageCodeToken);
            },




        }
    }
</script>

<style scoped>
    /* 登录框 */
    .login-div .input-group-addon {
        padding: 0;
        border: 0;
    }

    #login-modal h3 {
        text-align: center;
        margin-bottom: 20px;
    }

    #login-modal .modal-login {
        max-width: 400px;
    }

    #login-modal input:not(.remember) {
        height: 45px;
        font-size: 16px;
    }

    #login-modal .submit-button {
        height: 50px;
        font-size: 20px;
    }

    #login-modal .to-login-div {
        text-align: center;
    }
</style>
