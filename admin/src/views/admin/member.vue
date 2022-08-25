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
        <th>手机号</th>
        <th>密码</th>
        <th>昵称</th>
        <th>头像url</th>
        <th>注册时间</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="member in members">
        <td>{{member.id}}</td>
        <td>{{member.mobile}}</td>
        <td>{{member.password}}</td>
        <td>{{member.name}}</td>
        <td>{{member.photo}}</td>
        <td>{{member.registerTime}}</td>
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
    name: "business-member",
    data: function() {
      return {
        member: {},
        members: [],
      }
    },
    mounted: function() {

       this.$refs.pagination.size = 5;
       this.list(1);
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("business-member-sidebar");

    },
    methods: {


      /**
       * 列表查询
       */
      list(page) {

        Loading.show();
        axios.post(process.env.VUE_APP_SERVER + '/business/admin/member/list', {
          page: page,
          size: this.$refs.pagination.size,
        }).then((response) => {
          Loading.hide();
          let resp = response.data;
          this.members = resp.content.list;
          this.$refs.pagination.render(page, resp.content.total);

        })
      },

    }
  }
</script>