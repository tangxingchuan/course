<template>
  <div>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>


    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

      <div class="row">
          <div v-for="course in courses"  class="col-md-4 " >
                  <div class="thumbnail search-thumbnail">
                      <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" />
                      <img v-show="course.image" class="media-object" v-bind:src="course.image" />
                      <div class="caption">
                          <div class="clearfix">
                              <span class="pull-right label label-primary info-label">{{COURSE_LEVEL | optionKV(course.level)}}</span>

                              <span class="pull-right label label-primary info-label">{{COURSE_CHARGE | optionKV(course.charge)}}</span>

                              <span class="pull-right label label-primary info-label">{{COURSE_STATUS | optionKV(course.status)}}</span>
                          </div>

                          <h3 class="search-title">
                              <a href="#" class="blue">{{course.name}}</a>
                          </h3>


                          <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})" class="profile-activity clearfix">
                              <div>
                              <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png" />
                              <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image" />
                               <a class="user" href="#">{{teacher.name}}</a>
                                  <br>
                                  {{teacher.position}}
                                </div>
                              </div>


                          <p>
                              <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>&nbsp;
                          </p>
                          <p>{{course.summary}}</p>
                          <p>
                              <span class="badge badge-info">{{course.id}}</span>
                              <span class="badge badge-info">排序：{{course.sort}}</span>
                              <span class="badge badge-info">{{course.time | formatSecond}}</span>
                          </p>
                          <p>
                              <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                                  大章
                              </button>&nbsp;
                              <button v-on:click="editContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                                  内容
                              </button>&nbsp;
                              <button v-on:click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                                  排序
                              </button>&nbsp;

                              <button v-on:click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                                  编辑
                              </button>&nbsp;
                              <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                                  删除
                              </button>
                          </p>

                      </div>
                  </div>
          </div>
      </div>


    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>

          <div class="modal-body">
            <form class="form-horizontal">

                <div class="form-group">
                    <label class="col-sm-2 control-label">
                        分类
                    </label>
                    <div class="col-sm-10">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">封面</label>
                    <div class="col-sm-10">
                        <!--在file组件中，和组件不相关的业务代码应该由外部通过回调函数传进来。afterUpload()就是我们的外部回调函数-->
                        <flie v-bind:suffixs="['jpg', 'jpeg', 'png']"
                              v-bind:input-id="'image-upload'"
                              v-bind:text="'上传封面'"
                              v-bind:after-upload="afterUpload"
                              v-bind:use="FILE_USE.COURSE.key"></flie>
                        <div v-show="course.image" class="row">
                            <div class="col-md-6">
                                <img  v-bind:src="course.image" class="img-responsive" >
                            </div>
                        </div>
                    </div>
                </div>

              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="course.name" class="form-control">
                </div>
              </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">讲师</label>
                    <div class="col-sm-10">
                        <select v-model="course.teacherId" class="form-control">
                            <option v-for="o in teachers" v-bind:value="o.id">{{o.name}}</option>
                        </select>
                    </div>
                </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">概述</label>
                <div class="col-sm-10">
                  <input v-model="course.summary" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <input v-model="course.time" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">价格（元）</label>
                <div class="col-sm-10">
                  <input v-model="course.price" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">级别</label>
                <div class="col-sm-10">
                  <select v-model="course.level" class="form-control">
                    <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                  <select v-model="course.charge" class="form-control">
                    <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">状态</label>
                <div class="col-sm-10">
                  <select v-model="course.status" class="form-control">
                    <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">报名数</label>
                <div class="col-sm-10">
                  <input v-model="course.enroll" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input v-model="course.sort" class="form-control">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
      <!--富文本编辑器-->
      <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog modal-lg" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">内容编辑</h4>
                  </div>
                  <div class="modal-body">
                      <!--在富文本框中添加图片上传组件-->
                      <!--在file组件中，和组件不相关的业务代码应该由外部通过回调函数传进来。afterUpload()就是我们的外部回调函数-->
                      <file v-bind:input-id="'content-file-upload'"
                                               v-bind:text="'上传文件1'"
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
          </div>
      </div>
      <!--排序弹出框-->
      <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
                 <div class="modal-dialog" role="document">
                   <div class="modal-content">
                     <div class="modal-header">
                       <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                       <h4 class="modal-title">排序</h4>
                     </div>
                     <div class="modal-body">
                       <form class="form-horizontal">
                         <div class="form-group">
                           <label class="control-label col-lg-3">
                             当前排序
                           </label>
                           <div class="col-lg-9">
                             <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                           </div>
                         </div>
                         <div class="form-group">
                           <label class="control-label col-lg-3">
                             新排序
                           </label>
                           <div class="col-lg-9">
                             <input class="form-control" v-model="sort.newSort" name="newSort">
                           </div>
                         </div>
                       </form>
                     </div>
                     <div class="modal-footer">
                       <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                         <i class="ace-icon fa fa-times"></i>
                         取消
                       </button>
                       <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateSort()">
                         <i class="ace-icon fa fa-plus blue"></i>
                         更新排序
                       </button>
                     </div>
                   </div><!-- /.modal-content -->
                 </div><!-- /.modal-dialog -->
               </div><!-- /.modal -->
  </div>
</template>

<script>

  import axios from "axios";
  import Pagination from "../../components/pagination";
  import Flie from "../../components/file";

  export default {
    components: {Pagination,Flie},
    name: "business-course",
    data: function() {
      return {
        course: {},
        courses: [],
        COURSE_LEVEL: COURSE_LEVEL,
        COURSE_CHARGE: COURSE_CHARGE,
        COURSE_STATUS: COURSE_STATUS,
          FILE_USE:FILE_USE,
          tree:{},
          saveContentLabel:'',
          sort: {
              id: "",
              oldSort: 0,
              newSort: 0
          },
          teachers:[],
          files: [],


      }
    },
    mounted: function() {

       this.$refs.pagination.size = 5;
       this.allCategory();
       this.allTeacher();
       this.list(1);
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("business-course-sidebar");

    },
    methods: {
        /**
         * 点击【新增】
         */
        add:function() {

            this.course = {
                sort: this.$refs.pagination.total + 1
            };
            this.tree.checkAllNodes=false;
            $("#form-modal").modal("show");
        },

        /**
         * 点击【编辑】
         */
        edit: function (course) {

            this.course = $.extend({}, course);
            this.listCategory(course.id);
            $("#form-modal").modal("show");
        },

        /**
         * 列表查询
         */
        list: function (page) {

            Loading.show();
            axios.post(process.env.VUE_APP_SERVER +'/business/admin/course/list', {
                page: page,
                size: this.$refs.pagination.size,
            }).then((response) => {
                Loading.hide();
                let resp = response.data;
                this.courses = resp.content.list;
                this.$refs.pagination.render(page, resp.content.total);

            })
        },

        /**
         * 点击【保存】
         */
        save: function (page) {

            // 保存校验
            if (1 != 1
                || !Validator.require(this.course.name, "名称")
                || !Validator.length(this.course.name, "名称", 1, 50)
                || !Validator.length(this.course.summary, "概述", 1, 2000)
                || !Validator.length(this.course.image, "封面", 1, 100)
            ) {
                return;
            }

            let categorys = this.tree.getChangeCheckedNodes();
            if (Tool.isEmpty(categorys)) {
                Toast.warning('请选择分类！');
                return;
            }

            this.course.categorys = categorys;

            Loading.show();
            axios.post(process.env.VUE_APP_SERVER  + '/business/admin/course/save', this.course).then((response) => {
                Loading.hide();
                let resp = response.data;
                if (resp.success) {
                    $("#form-modal").modal("hide");
                    this.list(1);
                    Toast.success("保存成功！");
                } else {
                    Toast.warning(resp.message)
                }
            })
        },

      /**
       * 点击【删除】
       */
      del(id) {

          Confirm.show("删除课程后不可恢复，确认删除？", function () {
          Loading.show();
          axios.delete(process.env.VUE_APP_SERVER +  '/business/admin/course/delete/' +id).then((response)=>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
               this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      },

        /**
         *章节跳转到课程
         */
        toChapter(course){
          SessionStorage.set(SESSION_KEY_COURSE,course)
            this.$router.push("/business/chapter");
        },

        /**
         * 列表查询
         */
        allCategory() {
            Loading.show();
            axios.post(process.env.VUE_APP_SERVER + '/business/admin/category/all'
            ).then((response)=>{
                Loading.hide();
                let resp = response.data;
                this.categorys = resp.content;
                this.initTree();



            })
        },

        /**
         * 初始化树
         */
        initTree() {
            let setting = {
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        idKey: "id",
                        pIdKey: "parent",
                        rootPId: "00000000",
                        enable: true
                    }
                }
            };

            let zNodes = this.categorys;

            this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);

            // 展开所有的节点
            // _this.tree.expandAll(true);
        },

        /**
         * 查找课程下所有分类
         * @param courseId
         */
        listCategory(courseId) {
            Loading.show();
            axios.post(process.env.VUE_APP_SERVER + '/business/admin/course/list-category/' + courseId).then((res)=>{
                Loading.hide();
                console.log("查找课程下所有分类结果：", res);
                let response = res.data;
                let categorys = response.content;

                // 勾选查询到的分类
                this.tree.checkAllNodes(false);
                for (let i = 0; i < categorys.length; i++) {
                    let node = this.tree.getNodeByParam("id", categorys[i].categoryId);
                    this.tree.checkNode(node, true);
                }
            })
        },

        /**
         * 打开内容编辑框
         */
        editContent(course) {

              let id = course.id;
              this.course = course;
              $("#content").summernote({
                    focus: true,
                    height: 300
              });
              // 先清空历史文本
                  $("#content").summernote('code', '');
                  this.saveContentLabel='';
                    // 加载内容文件列表
                  this.listContentFiles();

              Loading.show();
              axios.get(process.env.VUE_APP_SERVER +'/business/admin/course/find-content/'  + id).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                       if (resp.success) {
                          $("#course-content-modal").modal({backdrop: 'static', keyboard: false});
                          if (resp.content) {
                                $("#content").summernote('code', resp.content.content);
                          }
                           // 定时自动保存
                             let saveContentInterval = setInterval(function() {
                                   this.saveContent();
                                 }, 5000);
                                       // 关闭内容框时，清空自动保存任务
                              $('#course-content-modal').on('hidden.bs.modal', function (e) {
                                  clearInterval(saveContentInterval);
                              })
                        } else {
                          Toast.warning(resp.message);
                        }
                  });
            },

            /**
              * 保存内容
              */
                saveContent () {
              let content = $("#content").summernote("code");
              axios.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
                    id: this.course.id,
                    content: content
              }).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                         /* Toast.success("内容保存成功");*/
                        let now = Tool.dateFormat('yy:MM:dd:hh:mm:ss');
                        this.saveContentLabel="最后保存时间 ：" +now;
                        } else {
                          Toast.warning(resp.message);
                        }
                  });
            },

        /*
        *
        * 课程排序
        * */
        openSortModal(course) {
            this.sort = {
                id: course.id,
                oldSort: course.sort,
                newSort: course.sort
            };

            $("#course-sort-modal").modal("show");
        },

        /**
         * 排序
         */
        updateSort(){
            if (this.sort.newSort === this.sort.oldSort) {
                Toast.warning("排序没有变化");
                return;
            }
            Loading.show();
            axios.post(process.env.VUE_APP_SERVER + "/business/admin/course/sort", this.sort).then((res) => {
                let response = res.data;

                if (response.success) {
                    Toast.success("更新排序成功");
                    $("#course-sort-modal").modal("hide");
                    this.list(1);
                } else {
                    Toast.error("更新排序失败");
                }
            });
        },

        /**
         * 讲师查询
         */
        allTeacher() {
            Loading.show();
            axios.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/all'
            ).then((response)=>{
                Loading.hide();
                let resp = response.data;
                this.teachers = resp.content;
            })
        },

        afterUpload(resp){

            let image = resp.content.path;
            this.course.image=image;
            this.$forceUpdate();
        },
        /**
         * 加载内容文件列表
          */
                listContentFiles() {

                axios.get(process.env.VUE_APP_SERVER   + '/business/admin/course-content-file/list/' + this.course.id).then((response)=>{
                      let resp = response.data;
                      if (resp.success) {
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
                axios.post(process.env.VUE_APP_SERVER +'/business/admin/course-content-file/save', file).then((response)=>{
                      let resp = response.data;
                      if (resp.success) {
                            Toast.success("上传文件成功");
                            this.files.push(resp.content);
                          }
                    });

                  },

              /**
                * 删除内容文件
                */
                  delFile(f) {
                let _this = this;
                Confirm.show("删除课程后不可恢复，确认删除？", function () {
                      axios.delete(process.env.VUE_APP_SERVER +'/business/admin/course-content-file/delete/' +f.id).then((response)=>{
                            let resp = response.data;
                            if (resp.success) {
                                  Toast.success("删除文件成功");
                                  Tool.removeObj(_this.files, f);
                                }
                          });
                    });
              },
           }

   }
</script>

<style scoped>
    .caption h3 {
        font-size: 20px;
    }

    @media (max-width: 1199px) {
        .caption h3 {
            font-size: 16px;
        }
    }

</style>
