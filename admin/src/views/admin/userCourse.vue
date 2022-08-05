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

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>登录名</th>
        <th>昵称</th>
        <th>密码</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="userCourse in userCourses">
        <td>{{userCourse.id}}</td>
        <td>{{userCourse.loginName}}</td>
        <td>{{userCourse.name}}</td>
        <td>{{userCourse.password}}</td>
      <td>
        <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="editPassword(userCourse)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-key bigger-120"></i>
            </button>
          <button v-on:click="edit(userCourse)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          <button v-on:click="del(userCourse.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>
      </td>
      </tr>
      </tbody>
    </table>

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
                <label class="col-sm-2 control-label">登录名</label>
                <div class="col-sm-10">
                  <input v-model="userCourse.loginName" v-bind:disabled="userCourse.id" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-10">
                  <input v-model="userCourse.name" class="form-control">
                </div>
              </div>
              <div v-show="!userCourse.id" class="form-group">
                <label class="col-sm-2 control-label">密码</label>
                <div class="col-sm-10">
                  <input v-model="userCourse.password" class="form-control">
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


      <div id="password-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">修改密码</h4>
                  </div>
                  <div class="modal-body">
                      <form class="form-horizontal">
                          <div  class="form-group">
                              <label class="col-sm-2 control-label">密码</label>
                              <div class="col-sm-10">
                                  <input v-model="userCourse.password" type="password" class="form-control">
                              </div>
                          </div>
                      </form>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                      <button v-on:click="savePassword()" type="button" class="btn btn-primary">保存密码</button>
                  </div>
              </div><!-- /.modal-content -->
          </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->

  </div>
</template>

<script>
  import axios from "axios";
  import Pagination from "../../components/pagination";
  export default {
    components: {Pagination},
    name: "system-userCourse",
    data: function() {
      return {
        userCourse: {},
        userCourses: [],
      }
    },
    mounted: function() {

       this.$refs.pagination.size = 5;
       this.list(1);
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("system-userCourse-sidebar");

    },
    methods: {
      /**
       * 点击【新增】
       */
      add() {

         this.userCourse = {};
        $("#form-modal").modal("show");
      },

      /**
       * 点击【编辑】
       */
      edit(userCourse) {

         this.userCourse = $.extend({}, userCourse);
        $("#form-modal").modal("show");
      },

      /**
       * 列表查询
       */
      list(page) {

        Loading.show();
         axios.post(process.env.VUE_APP_SERVER + '/system/admin/userCourse/list', {
          page: page,
          size:  this.$refs.pagination.size,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
           this.userCourses = resp.content.list;
           this.$refs.pagination.render(page, resp.content.total);

        })
      },

      /**
       * 点击【保存】
       */
      save() {

          this.userCourses.password = hex_md5(this.userCourses.password + KEY);

        // 保存校验
        if (1 != 1
          || !Validator.require( this.userCourse.loginName, "登录名")
          || !Validator.length( this.userCourse.loginName, "登录名", 1, 60)
          || !Validator.length( this.userCourse.name, "昵称", 1, 50)
          || !Validator.require( this.userCourse.password, "密码")
        ) {
          return;
        }

        Loading.show();
        axios.post(process.env.VUE_APP_SERVER + '/system/admin/userCourse/save',  this.userCourse).then((response)=>{
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

        Confirm.show("删除用户后不可恢复，确认删除？", function () {
          Loading.show();
          axios.delete(process.env.VUE_APP_SERVER + '/system/admin/userCourse/delete/' + id).then((response)=>{
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
         * 点击【重置密码】
         */
        editPassword(userCourse) {

            this.userCourse = $.extend({}, userCourse);
            this.userCourse.password = null;
            $("#password-modal").modal("show");
        },

        /**
         * 点击【保存密码】
         */
        savePassword() {

            this.userCourse.password = hex_md5(this.userCourse.password + KEY);
            Loading.show();
            axios.post(process.env.VUE_APP_SERVER + '/system/admin/userCourse/save-password',  this.userCourse).then((response)=>{
                Loading.hide();
                let resp = response.data;
                if (resp.success) {
                    $("#password-modal").modal("hide");
                    this.list(1);
                    Toast.success("保存成功！");
                } else {
                    Toast.warning(resp.message)
                }
            })
        },

    }
  }
</script>
