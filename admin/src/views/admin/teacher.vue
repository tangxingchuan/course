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
          <div v-for="teacher in teachers" class="col-md-3 ">
              <div>
                 <span class="profile-picture">
             <img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty"
                  src="/ace/assets/images/avatars/profile-pic.jpg" v-bind:title="teacher.intro"/>
             <img v-show="teacher.image" class="editable img-responsive editable-click editable-empty" v-bind:src="teacher.image" v-bind:title="teacher.intro"/>
           </span>

                  <div class="space-4"></div>

                  <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
                      <div class="inline position-relative">
                          <a href="javascript:;" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                              <i class="ace-icon fa fa-circle light-green"></i>
                              &nbsp;
                              <span class="white">{{teacher.position}}</span>
                          </a>
                      </div>
                  </div>
              </div>

              <div class="space-6"></div>

              <div class="text-center">
                  <a href="javascript:;" class="text-info bigger-110" v-bind:title="teacher.motto">
                      <i class="ace-icon fa fa-user"></i>
                      {{teacher.name}}【{{teacher.nickname}}】
                  </a>
              </div>


              <div class="space-6"></div>

              <div class="profile-social-links align-center">

                  <div class="profile-social-links align-center">
                      <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
                          <i class="ace-icon fa fa-pencil bigger-120"></i>
                      </button>
                      &nbsp;
                      <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
                          <i class="ace-icon fa fa-trash-o bigger-120"></i>
                      </button>
                  </div>

                  <div class="hr hr16 dotted"></div>

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
                <label class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-10">
                  <input v-model="teacher.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-10">
                  <input v-model="teacher.nickname" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">头像</label>
                <div class="col-sm-10">

                    <!--在file组件中，和组件不相关的业务代码应该由外部通过回调函数传进来。afterUpload()就是我们的外部回调函数-->
                    <big-file v-bind:suffixs="['jpg', 'jpeg', 'png']"
                          v-bind:input-id="'image-upload'"
                          v-bind:text="'上传头像'"
                          v-bind:after-upload="afterUpload"
                          v-bind:use="FILE_USE.TEACHER.key"></big-file>

                    <div v-show="teacher.image" class="row">
                        <div class="col-md-4">
                            <img  v-bind:src="teacher.image" class="img-responsive" >
                        </div>
                    </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">职位</label>
                <div class="col-sm-10">
                  <input v-model="teacher.position" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">座右铭</label>
                <div class="col-sm-10">
                  <input v-model="teacher.motto" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">简介</label>
                <div class="col-sm-10">
                    <textarea v-model="teacher.intro" class="form-control" rows="5"></textarea>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
              &nbsp;
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
      </div>
  </div>
</template>

<script>
  import axios from "axios";
  import Pagination from "../../components/pagination";
  import BigFile from "../../components/big-file";
  export default {
    components: {BigFile, Pagination},
    name: "business-teacher",
    data: function() {
      return {
        teacher: {},
        teachers: [],
        FILE_USE:FILE_USE,
      }
    },
    mounted: function() {

       this.$refs.pagination.size = 5;
       this.list(1);
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("business-teacher-sidebar");

    },
    methods: {
      /**
       * 点击【新增】
       */
      add() {

         this.teacher = {};
        $("#form-modal").modal("show");
      },

      /**
       * 点击【编辑】
       */
      edit(teacher) {

         this.teacher = $.extend({}, teacher);
        $("#form-modal").modal("show");
      },

      /**
       * 列表查询
       */
      list(page) {

        Loading.show();
         axios.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/list', {
          page: page,
          size:  this.$refs.pagination.size,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
           this.teachers = resp.content.list;
           this.$refs.pagination.render(page, resp.content.total);

        })
      },

      /**
       * 点击【保存】
       */
      save() {


        // 保存校验
        if (1 != 1
          || !Validator.require( this.teacher.name, "姓名")
          || !Validator.length( this.teacher.name, "姓名", 1, 50)
          || !Validator.length( this.teacher.nickname, "昵称", 1, 50)
          || !Validator.length( this.teacher.image, "头像", 1, 100)
          || !Validator.length( this.teacher.position, "职位", 1, 50)
          || !Validator.length( this.teacher.motto, "座右铭", 1, 50)
          || !Validator.length( this.teacher.intro, "简介", 1, 500)
        ) {
          return;
        }

        Loading.show();
        axios.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/save',  this.teacher).then((response)=>{
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

        Confirm.show("删除讲师后不可恢复，确认删除？", function () {
          Loading.show();
          axios.delete(process.env.VUE_APP_SERVER + '/business/admin/teacher/delete/' + id).then((response)=>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
               this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      },

        //在file组件中，和组件不相关的业务代码应该由外部通过回调函数传进来。
        afterUpload(resp){

           let image = resp.content.path;
            this.teacher.image=image;
            this.$forceUpdate();
        },


    }
  }
</script>
