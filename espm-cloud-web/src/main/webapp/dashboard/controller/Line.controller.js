sap.ui.define([
        'jquery.sap.global',
        'sap/ui/core/mvc/Controller',
        'sap/ui/model/json/JSONModel',
        'sap/viz/ui5/data/FlattenedDataset',
        'sap/viz/ui5/format/ChartFormatter',
        'sap/viz/ui5/api/env/Format',
    ], function(jQuery, Controller, JSONModel, FlattenedDataset, ChartFormatter, Format) {
    "use strict";

    var Controller = Controller.extend("com.sap.espm.shop.controller.Line", {

        dataPath : "model",

        settingsModel : {

            series : {
                name : "Series",
                defaultSelected : 1,
                values : [{
                    name : "1 Series",
                    value : ["Revenue"]
                }, {
                    name : '2 Series',
                    value : ["Revenue", "Cost"]
                }]
            },
            switch : {
                name : "LED Switch",
                defaultState : true
            },
            axisTitle : {
                name : "Axis Title",
                defaultState : false
            },
            dimensions: {
                Small: [{
                    name: 'Seasons',
                    value: "{Seasons}",
                }],
                Medium: [{
                    name: 'Week',
                    value: "{Week}",
                }],
                Large: [{
                    name: 'Week',
                    value: "{Week}",
                }]
            },
            measures: [{
               name: 'Revenue',
               value: '{Revenue}'
            },{
               name: 'Cost',
               value: '{Cost}'
            }]
        },

        oVizFrame : null,

        onInit : function (evt) {
            Format.numericFormatter(ChartFormatter.getInstance());
            var formatPattern = ChartFormatter.DefaultPattern;
            // set explored app's demo model on this sample

            var oModel = new JSONModel(this.settingsModel);
            oModel.setDefaultBindingMode(sap.ui.model.BindingMode.OneWay);
            this.getView().setModel(oModel);

            var oVizFrame = this.oVizFrame = this.getView().byId("idVizFrame");
            oVizFrame.setVizProperties({
                plotArea: {
                    dataLabel: {
                        formatString:formatPattern.SHORTFLOAT_MFD2,
                        visible: true
                    }
                },
                valueAxis: {
                    label: {
                        formatString: formatPattern.SHORTFLOAT
                    },
                    title: {
                        visible: true,
                        text: '温湿度'
                    }
                },
                categoryAxis: {
                    title: {
                        visible: true,
                        text: '时间'
                    }
                },
                title: {
                    visible: true,
                    text: 'Huminity & Temperature sensor'
                }
            });
            // var dataModel = new JSONModel(this.dataPath + "/betterMedium.json");
            // oVizFrame.setModel(dataModel);

            var oPopOver = this.getView().byId("idPopOver");
            oPopOver.connect(oVizFrame.getVizUid());
            oPopOver.setFormatString(formatPattern.STANDARDFLOAT);

            // InitPageUtil.initPageSettings(this.getView());
        },
        onAfterRendering : function(){
            // var datasetRadioGroup = this.getView().byId('datasetRadioGroup');
            // datasetRadioGroup.setSelectedIndex(this.settingsModel.dataset.defaultSelected);
            //
            // var seriesRadioGroup = this.getView().byId('seriesRadioGroup');
            // seriesRadioGroup.setSelectedIndex(this.settingsModel.series.defaultSelected);
        },

        onDatasetSelected : function(oEvent){
            // var datasetRadio = oEvent.getSource();
            // if(this.oVizFrame && datasetRadio.getSelected()){
            //     var bindValue = datasetRadio.getBindingContext().getObject();
            //     var dataset = {
            //         data: {
            //             path: "/measurement"
            //         }
            //     };
            //     var dim = this.settingsModel.dimensions[bindValue.name];
            //     dataset.dimensions = dim;
            //     dataset.measures = this.settingsModel.measures;
            //     var oDataset = new FlattenedDataset(dataset);
            //     this.oVizFrame.setDataset(oDataset);
            //     var dataModel = new JSONModel(this.dataPath + bindValue.value);
            //     this.oVizFrame.setModel(dataModel);
            //
            //     var feedCategoryAxis = this.getView().byId('categoryAxisFeed');
            //     this.oVizFrame.removeFeed(feedCategoryAxis);
            //     var feed = [];
            //     for (var i = 0; i < dim.length; i++) {
            //         feed.push(dim[i].name);
            //     }
            //     feedCategoryAxis.setValues(feed);
            //     this.oVizFrame.addFeed(feedCategoryAxis);
            // }
        },

        onSeriesSelected : function(oEvent){
            var seriesRadio = oEvent.getSource();
            if(this.oVizFrame && seriesRadio.getSelected()){
                var bindValue = seriesRadio.getBindingContext().getObject();

                var feedValueAxis = this.getView().byId('valueAxisFeed');
                this.oVizFrame.removeFeed(feedValueAxis);
                feedValueAxis.setValues(bindValue.value);
                this.oVizFrame.addFeed(feedValueAxis);
            }
        },

        onSwitchChanged : function(oEvent){
            if(this.oVizFrame){
                this.oVizFrame.setVizProperties({
                    plotArea: {
                        dataLabel: {
                            visible: oEvent.getParameter('state')
                        }
                    }
                });
            }
        },

        onAxisTitleChanged : function(oEvent){
            if(this.oVizFrame){
                var state = oEvent.getParameter('state');
                this.oVizFrame.setVizProperties({
                    valueAxis: {
                        title: {
                            visible: state
                        }
                    },
                    categoryAxis: {
                        title: {
                            visible: state
                        }
                    }
                });
            }
        }
    });

    return Controller;

});