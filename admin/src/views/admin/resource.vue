<template>
  <div>
    <p>
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

      <div class="row">
          <div class="col-md-6">
              <textarea id="resource-textarea" class="form-group"/>
              <br>
              <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
                  保存
              </button>
          </div>
      </div>

    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>名称</th>
        <th>页面</th>
        <th>请求</th>
        <th>父id</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="resource in resources">
        <td>{{resource.id}}</td>
        <td>{{resource.name}}</td>
        <td>{{resource.page}}</td>
        <td>{{resource.request}}</td>
        <td>{{resource.parent}}</td>
      <td>
        <div class="hidden-sm hidden-xs btn-group">
          <button v-on:click="edit(resource)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          <button v-on:click="del(resource.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>
      </td>
      </tr>
      </tbody>
    </table>


  </div>
</template>

<script>
  import axios from "axios";
  import Pagination from "../../components/pagination";
  export default {
    components: {Pagination},
    name: "system-resource",
    data: function() {
      return {
        resource: {},
        resources: [],
        resourcesStr:'',
      }
    },
    mounted: function() {

       this.$refs.pagination.size = 5;
       this.list(1);
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("system-resource-sidebar");

    },
    methods: {



      /**
       * 列表查询
       */
      list(page) {

        Loading.show();
         axios.post(process.env.VUE_APP_SERVER + '/system/admin/resource/list', {
          page: page,
          size:  this.$refs.pagination.size,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
           this.resources = resp.content.list;
           this.$refs.pagination.render(page, resp.content.total);

        })
      },

      /**
       * 点击【保存】
       */
      save() {

          if (Tool.isEmpty(this.resourcesStr)){
              Toast.warning("资源不能为空")
              return
          }

          let json  = JSON.parse(this.resourcesStr);

        Loading.show();
        axios.post(process.env.VUE_APP_SERVER + '/system/admin/resource/save', json).then((response)=>{
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

        Confirm.show("删除资源后不可恢复，确认删除？", function () {
          Loading.show();
          axios.delete(process.env.VUE_APP_SERVER + '/system/admin/resource/delete/' + id).then((response)=>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
               this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      }
    }
  }
</script>
