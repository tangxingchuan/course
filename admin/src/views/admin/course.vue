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
          <div v-for="course in courses"  class="col-md-4">
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

                          <p>
                              <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>&nbsp;
                          </p>
                          <p>{{course.summary}}</p>
                          <p>
                              <span class="badge badge-info">{{course.id}}</span>
                              <span class="badge badge-info">排序：{{course.sort}}</span>
                              <span class="badge badge-info">时长：{{course.time | formatSecond}}</span>
                          </p>
                          <p>
                              <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                                  大章
                              </button>&nbsp;
                              <button v-on:click="editContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                                  内容
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
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="course.name" class="form-control">
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
                <label class="col-sm-2 control-label">封面</label>
                <div class="col-sm-10">
                  <input v-model="course.image" class="form-control">
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
      <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog modal-lg" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">内容编辑</h4>
                  </div>
                  <div class="modal-body">
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
  </div>
</template>

<script>

  import axios from "axios";
  import Pagination from "../../components/pagination";
  export default {
    components: {Pagination},
    name: "business-course",
    data: function() {
      return {
        course: {},
        courses: [],
        COURSE_LEVEL: COURSE_LEVEL,
        COURSE_CHARGE: COURSE_CHARGE,
        COURSE_STATUS: COURSE_STATUS,
          tree:{},
          saveContentLabel:'',
      }
    },
    mounted: function() {

       this.$refs.pagination.size = 5;
       this.allCategory();
       this.list(1);
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("business-course-sidebar");

    },
    methods: {
        /**
         * 点击【新增】
         */
        add:function() {

            this.course = {};
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
            axios.post(process.env.VUE_APP_SERVER + '/business/admin/course/list', {
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
            axios.post(process.env.VUE_APP_SERVER + '/business/admin/course/save', this.course).then((response) => {
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
          axios.delete(process.env.VUE_APP_SERVER + '/business/admin/course/delete/' + id).then((response)=>{
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
          SessionStorage.set('course',course)
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
        initTree: function () {
            let setting = {
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        idKey: 'id',
                        pIdKey: 'parent',
                        rootPId: '00000000',
                        enable: true
                    }
                }
            };

            let zNodes = this.categorys;
            this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);

            //展开所有的节点
            //this.tree.expandAll(true);

        },

        /**
         *查找课程下的所有节点
         * @param courseId
         */
        listCategory: function (courseId) {

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
            });
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

              Loading.show();
              axios.get(process.env.VUE_APP_SERVER + '/business/admin/course/find-content/' + id).then((response)=>{
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
                        this.saveContentLabel="最后保存时间 ："+now;
                        } else {
                          Toast.warning(resp.message);
                        }
                  });
            }
        }
  }
</script>

<style scoped>
    .caption h3 {
        font-size: 20px;
    }
</style>
