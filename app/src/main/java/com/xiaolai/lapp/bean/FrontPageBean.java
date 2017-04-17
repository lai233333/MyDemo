package com.xiaolai.lapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class FrontPageBean implements Serializable{
    private long version;
    private List<DataBeanX> data;

    public long getVersion() {
        return version;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        private int id;
        private String name;
        private String desc;
        private int style;
        private int isNameDisplay;
        private ActionBean action;
        private List<DataBean> data;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }

        public int getStyle() {
            return style;
        }

        public int getIsNameDisplay() {
            return isNameDisplay;
        }

        public ActionBean getAction() {
            return action;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public void setIsNameDisplay(int isNameDisplay) {
            this.isNameDisplay = isNameDisplay;
        }

        public void setAction(ActionBean action) {
            this.action = action;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class ActionBean {
            /**
             * type : 0
             */

            private int type;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class DataBean {
            /**
             * id : 3926
             * name : 长大的童话
             * desc : 林宥嘉
             * picUrl : http://pic.xiami.net/images/trade/ams_homepage/195/58370f164f6dc_9754208_1480003350.jpg
             * action : {"type":19,"value":"2102656296"}
             * albumRightKey : {"buyFlag":false,"price":0,"vipFree":null,"quality":null,"active":null,"count":-1,"buy":false,"dmsg":""}
             * status : 1
             * isExclusive : 1
             */

            private int id;
            private String name;
            private String desc;
            // 图片url
            private String picUrl;
            private ActionBeanX action;
            private AlbumRightKeyBean albumRightKey;
            private int status;
            private int isExclusive;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getDesc() {
                return desc;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public ActionBeanX getAction() {
                return action;
            }

            public AlbumRightKeyBean getAlbumRightKey() {
                return albumRightKey;
            }

            public int getStatus() {
                return status;
            }

            public int getIsExclusive() {
                return isExclusive;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public void setAction(ActionBeanX action) {
                this.action = action;
            }

            public void setAlbumRightKey(AlbumRightKeyBean albumRightKey) {
                this.albumRightKey = albumRightKey;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setIsExclusive(int isExclusive) {
                this.isExclusive = isExclusive;
            }

            public static class ActionBeanX {
                /**
                 * type : 19
                 * value : 2102656296
                 */

                private int type;
                private String value;

                public int getType() {
                    return type;
                }

                public String getValue() {
                    return value;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
            public static class AlbumRightKeyBean {
                /**
                 * buyFlag : false
                 * price : 0
                 * vipFree : null
                 * quality : null
                 * active : null
                 * count : -1
                 * buy : false
                 * dmsg :
                 */

                private boolean buyFlag;
                private int price;
                private Object vipFree;
                private Object quality;
                private Object active;
                private int count;
                private boolean buy;
                private String dmsg;

                public boolean isBuyFlag() {
                    return buyFlag;
                }

                public int getPrice() {
                    return price;
                }

                public Object getVipFree() {
                    return vipFree;
                }

                public Object getQuality() {
                    return quality;
                }

                public Object getActive() {
                    return active;
                }

                public int getCount() {
                    return count;
                }

                public boolean isBuy() {
                    return buy;
                }

                public String getDmsg() {
                    return dmsg;
                }

                public void setBuyFlag(boolean buyFlag) {
                    this.buyFlag = buyFlag;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public void setVipFree(Object vipFree) {
                    this.vipFree = vipFree;
                }

                public void setQuality(Object quality) {
                    this.quality = quality;
                }

                public void setActive(Object active) {
                    this.active = active;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public void setBuy(boolean buy) {
                    this.buy = buy;
                }

                public void setDmsg(String dmsg) {
                    this.dmsg = dmsg;
                }
            }
        }
    }
}
