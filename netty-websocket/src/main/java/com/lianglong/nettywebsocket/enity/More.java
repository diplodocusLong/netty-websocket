package com.lianglong.nettywebsocket.enity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lianglong
 * @date 2020/5/2
 */

@NoArgsConstructor
@Data
public class More {


    /**
     * animals : {"dog":[{"name":"Rufus","breed":"labrador","count":1,"twoFeet":false},{"name":"Marty","breed":"whippet","count":1,"twoFeet":false}],"cat":{"name":"Matilda"}}
     */

    private AnimalsEnity animals;

    @NoArgsConstructor
    @Data
    public static class AnimalsEnity {
        /**
         * dog : [{"name":"Rufus","breed":"labrador","count":1,"twoFeet":false},{"name":"Marty","breed":"whippet","count":1,"twoFeet":false}]
         * cat : {"name":"Matilda"}
         */

        private CatEnity cat;
        private List<DogEnity> dog;

        @NoArgsConstructor
        @Data
        public static class CatEnity {
            /**
             * name : Matilda
             */

            private String name;
        }

        @NoArgsConstructor
        @Data
        public static class DogEnity {
            /**
             * name : Rufus
             * breed : labrador
             * count : 1
             * twoFeet : false
             */

            private String name;
            private String breed;
            private int count;
            private boolean twoFeet;
        }
    }
}
