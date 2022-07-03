<template>
    <div>
        <!--跳转栏-->
        <h4 class="lighter">
            <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
            <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
        </h4>
        <hr>

<!--模态框 -->
    <div class="modal-body">
        <!--在富文本框中添加图片上传组件-->
        <!--在file组件中，和组件不相关的业务代码应该由外部通过回调函数传进来。afterUpload()就是我们的外部回调函数-->
        <file v-bind:input-id="'content-file-upload'"
              v-bind:text="'上传文件'"
              v-bind:suffixs="['jpg', 'jpeg', 'png', 'mp4']"
              v-bind:use="FILE_USE.COURSE.key"
              v-bind:after-upload="afterUploadContentFile"></file>
        <br>

        <table id="file-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>名称</th>
                <th>地址</th>
                <th>大小</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="(f, i) in files" v-bind:key="f.id">
                <td>{{f.name}}</td>
                <td>{{f.url}}</td>
                <td>{{f.size | formatFileSize}}</td>
                <td>
                    <button v-on:click="delFile(f)" class="btn btn-white btn-xs btn-warning btn-round">
                        <i class="ace-icon fa fa-times red2"></i>
                        删除
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-lg-12">
                    {{saveContentLabel}}
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-12">
                    <div id="content"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
            <i class="ace-icon fa fa-times"></i>
            取消
        </button>
        <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveContent()">
            <i class="ace-icon fa fa-plus blue"></i>
            保存
        </button>
    </div>

</div>
</template>

<script>
    import axios from "axios";
    import Flie from "../../components/file";
    export default {
        name: "content",
        components: {Flie,},
        data: function() {
            return {
                course: {},
                FILE_USE: FILE_USE,
                saveContentLabel: "",
                files: [],
                saveContentInterval: {},

            }
        },
        mounted: function () {


            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            if ( Tool.isEmpty(course) ) {
                this.$router.push("/welcome");
            }
            this.course = course;

            this.init();

            // sidebar激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");

        },
        destroyed: function () {

            console.log("组件销毁");
            clearInterval(this.saveContentInterval);
        },
        methods: {

            /**
             * 打开内容编辑框
             */
            init() {

                let course = this.course;
                let id = course.id;
                $("#content").summernote({
                    focus: true,
                    height: 300
                });

                // 先清空历史文本
                $("#content").summernote('code', '');
                this.saveContentLabel = "";

                // 加载内容文件列表
                this.listContentFiles();

                Loading.show();
                axios.get(process.env.VUE_APP_SERVER + '/business/admin/course/find-content/' + id).then((response) => {
                    Loading.hide();
                    let resp = response.data;

                    if ( resp.success ) {
                        if ( resp.content ) {
                            $("#content").summernote('code', resp.content.content);
                        }

                        // 定时自动保存
                        this.saveContentInterval = setInterval(function () {
                            this.saveContent();
                        }, 5000);
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },

            /**
             * 保存内容
             */
            saveContent() {

                let content = $("#content").summernote("code");
                axios.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
                    id: this.course.id,
                    content: content
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if ( resp.success ) {
                        // Toast.success("内容保存成功");
                        // let now = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
                        let now = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
                        this.saveContentLabel = "最后保存时间："+
                        now;
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },

            /**
             * 加载内容文件列表
             */
            listContentFiles() {
                axios.get(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/list/' + this.course.id).then((response) => {
                    let resp = response.data;
                    if ( resp.success ) {
                        this.files = resp.content;
                    }
                });
            },

            /**
             * 上传内容文件后，保存内容文件记录
             */
            afterUploadContentFile(response) {

                console.log("开始保存文件记录");
                let file = response.content;
                file.courseId = this.course.id;
                file.url = file.path;
                axios.post(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/save', file).then((response) => {
                    let resp = response.data;
                    if ( resp.success ) {
                        Toast.success("上传文件成功");
                        this.files.push(resp.content);
                    }
                });

            },

            /**
             * 删除内容文件
             */
            delFile(f) {
                Confirm.show("删除课程后不可恢复，确认删除？", function () {
                    axios.delete(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/delete/' + f.id).then((response) => {
                        let resp = response.data;
                        if ( resp.success ) {
                            Toast.success("删除文件成功");
                            Tool.removeObj(this.files, f);
                        }
                    });
                });
            },
        }


    }
</script>

<style scoped>

</style>
