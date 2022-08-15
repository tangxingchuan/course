<template>

    <div>
        <div class="album py-5 bg-light">
            <div class="container">
                <div class="title1"></div>
                <div class="row">
                    <div v-for=" o in courses" class="col-md-4">
                        <the v-bind:course="o"></the>
                    </div>
                    <h3 v-show="courses.length===0">课程还未上架</h3>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    import axios from "axios";
    import TheHeader from "../components/the-header";
    import TheFooter from "../components/the-footer";
    import TheCourse from "../components/the-course";

    export default {
        components: {TheFooter, TheHeader, TheCourse},
        comments: {},
        name: "list",
        data: function () {

            return {
                courses: {},
            }
        },

        mounted() {

            this.listCourse(1);


        },
        methods: {
            /**
             * 查询新上好课
             */
            listCourse(page) {

                axios.get(process.env.VUE_APP_SERVER + "/business/web/course/list",{

                    page:page,
                    size:3
                }).then((response) => {

                    console.log("查询新上好课结果", response);
                    let res = response.data;

                    if ( res.success ) {
                        this.courses = res.content;
                    }
                }).catch((response) => {

                    console.log('error', response);
                })

            },


        },


    }
</script>

<style scoped>

</style>
