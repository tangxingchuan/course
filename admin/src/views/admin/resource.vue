<template>
    <div>
        <p>
            <button v-on:click="list()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh"></i>
                刷新
            </button>
        </p>

        <div class="row">
            <div class="col-md-6">
                <textarea id="resource-textarea" class="form-control" v-model="resourcesStr" name="resource" rows="10"></textarea>

                <br>
                <button id="save-btn" type="button" class="btn btn-primary" v-on:click="save()">
                    保存
                </button>
            </div>
            <div class="col-md-6">
                <ul id="tree" class="ztree"></ul>
            </div>
        </div>

    </div>
</template>

<script>
  import axios from "axios";


  export default {
    name: "system-resource",
    data: function() {
      return {
        resource: {},
        resources: [],
        resourcesStr:'',
        tree:{},
      }
    },
    mounted: function() {

       this.list();
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("system-resource-sidebar");

    },
    methods: {



      /**
       * 列表查询
       */
      list() {

        Loading.show();
         axios.get(process.env.VUE_APP_SERVER + '/system/admin/resource/load-tree', {
        }).then((res)=>{
          Loading.hide();
          let response = res.data;
           this.resources = response.content;

           //初始化树
             this.initTree();

        })
      },

      /**
       * 点击【保存】
       */
      save() {

          if (Tool.isEmpty(this.resourcesStr)){
              Toast.warning("资源不能为空");
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
      },

        /**
         * 初始化资源树
         */
        initTree() {
            let setting = {

                data: {
                    simpleData: {
                        idKey: "id",
                        pIdKey: "parent",
                        rootPId: "",
                        //enable: true
                    }
                }
            };
            this.tree = $.fn.zTree.init($("#tree"), setting, this.resources);

            // 展开所有的节点
            this.tree.expandAll(true);
        },
    }
  }
</script>
