<template>

    <div>
        <TheHeader></TheHeader>

        <router-view></router-view>

        <the-footer></the-footer>

    </div>

</template>

<script>

 import TheHeader from "./components/the-header";
 import TheFooter from "./components/the-footer";
export default {
  name: 'App',
    components:{TheFooter, TheHeader}

}
</script>


