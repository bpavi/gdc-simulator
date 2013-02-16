Ext
		.define(
				'GDC.node.DCViewPanel',
				{
					extend : 'Ext.panel.Panel',
					xtype : 'nodeDcpanel',
					closable : true,
					autoScroll : true,
					layout : {
						type : 'table',
						columns : 2,
						tdAttrs : {
							valign : 'top',
							style : {
								padding : '10 10 10 10'
							}
						}
					},
					items : [
							{
								itemId : 'banner',
								xtype : 'panel',
								width : '100%',
								height : 40,
								styleHtmlContent : true,
								styleHtmlCls : 'banner',
								html : '',
								colspan : 2
							},
							{
								xtype : 'panel',
								title : 'Information',
								itemId : 'information',
								collapsible : true,
								width : 700,
								bodyStyle : 'padding:10px',
								items : [ {
									xtype : 'image',
									style : 'float:left;',
									width : 100,
									height : 100,
									margin : 10,
									src : 'image/data_center.jpg'
								}, {
									xtype : 'component',
									itemId : 'description',
									width : 500,
									style : 'float:left;',
									margin : 10
								} ]
							},
							{
								xtype : 'panel',
								itemId : 'evaluation',
								title : 'Evaluation',
								collapsible : true,
								height : 450,
								width : 320,
								rowspan : 3,
								bodyStyle : 'padding:10px',
								items : [
										{
											xtype : 'gdcEvalRadarChart',
											itemId : 'eval',
											width : 300,
											height : 200,
											store : Ext.create(
													'Ext.data.ArrayStore', {
														// reader configs
														fields : [ 'name', {
															name : 'value',
															type : 'float'
														} ]
													})
										},
										{
											xtype : 'component',
											styleHtmlContent : true,
											styleHtmlCls : 'desc',
											width : 270,
											padding : 10,
											html : "<div>This is an evaluation of the data center.</div>"
													+ "<ul>"
													+ "<li>"
													+ "<label>"
													+ "Stability:"
													+ "</label>"
													+ "asdfasdfsdfasdfasdf"
													+ "</li>"
													+ "<li>"
													+ "<label>"
													+ "Efficiency:"
													+ "</label>"
													+ "asdfasdfsdfasdfasdf"
													+ "</li>"
													+ "<li>"
													+ "<label>"
													+ "Capacity:"
													+ "</label>"
													+ "asdfasdfsdfasdfasdf"
													+ "</li>"
													+ "<li>"
													+ "<label>"
													+ "Availability:"
													+ "</label>"
													+ "asdfasdfsdfasdfasdf"
													+ "</li>" + "</ul>"
										} ]
							}, {
								itemId : 'performance',
								xtype : 'panel',
								title : 'Performance',
								bodyStyle : 'padding:10px',
								collapsible : true,
								tbar : [ {
									text : 'Reload',
									handler : function() {
									}
								} ],
								items : [ {
									xtype : 'gdcGaugeChart',
									itemId : 'healthChart',
									style : 'background:#fff',
									store : Ext.create('Ext.data.ArrayStore', {
										// reader configs
										fields : [ {
											name : 'data1',
											type : 'float'
										} ]
									}),
									axes : [ {
										type : 'gauge',
										position : 'gauge',
										title : 'Health',
										minimum : 0,
										maximum : 100,
										steps : 4,
										margin : 7
									} ]
								}, {
									xtype : 'gdcGaugeChart',
									itemId : 'powerChart',
									style : 'background:#fff',
									store : Ext.create('Ext.data.ArrayStore', {
										// reader configs
										fields : [ {
											name : 'data1',
											type : 'float'
										} ]
									}),
									axes : [ {
										type : 'gauge',
										position : 'gauge',
										minimum : 0,
										maximum : 100,
										steps : 4,
										margin : 7,
										title : 'Power\nGeneration'
									} ]
								}, {
									xtype : 'gdcGaugeChart',
									itemId : 'capacityChart',
									style : 'background:#fff',
									store : Ext.create('Ext.data.ArrayStore', {
										// reader configs
										fields : [ {
											name : 'data1',
											type : 'float'
										} ]
									}),
									axes : [ {
										type : 'gauge',
										position : 'gauge',
										minimum : 0,
										maximum : 100,
										steps : 4,
										margin : 7,
										title : 'Capacity'
									} ]
								} ]
							}, {
								xtype : 'panel',
								itemId : 'history',
								title : 'History',
								height : 340,
								width : 700,
								bodyStyle : 'padding:10px',
								collapsible : true,
								items : [ {
									xtype : 'gdcLineChart',
									width : 650,
									height : 250,
									itemId : 'powerHistory',
									store : Ext.create('Ext.data.ArrayStore', {
										// reader configs
										fields : [ 'time', {
											name : 'value',
											type : 'float'
										} ]
									}),
									axes : [ {
										type : 'Numeric',
										minimum : 0,
										position : 'left',
										fields : [ 'value' ],
										title : 'Power(MW)',
										minorTickSteps : 1,
										grid : {
											odd : {
												opacity : 1,
												fill : '#ddd',
												stroke : '#bbb',
												'stroke-width' : 0.5
											}
										}
									}, {
										type : 'Category',
										position : 'bottom',
										fields : [ 'time' ],
										title : 'Time'
									} ],
									series : [ {
										type : 'line',
										highlight : {
											size : 7,
											radius : 7
										},
										axis : 'left',
										xField : 'time',
										yField : 'value',
										markerConfig : {
											type : 'cross',
											size : 4,
											radius : 4,
											'stroke-width' : 0
										}
									} ]
								} ]
							} ],
					updateId : function(id) {
						this.id = id;
						Ext.ComponentManager.register(this);
					},
					loadData : function(datas) {
						this.getComponent('information').getComponent(
								'description').html = datas.description;
						this.getComponent('performance').getComponent(
								'healthChart').store
								.loadData([ [ datas.health ] ]);
						this.getComponent('performance').getComponent(
								'capacityChart').store
								.loadData([ [ datas.capacity ] ]);
						this.getComponent('performance').getComponent(
								'powerChart').store
								.loadData([ [ datas.power ] ]);

						var data = [ {
							time : '12:00',
							value : 240
						}, {
							time : '12:10',
							value : 500
						}, {
							time : '12:20',
							value : 350
						}, {
							time : '12:30',
							value : 900
						}, {
							time : '12:40',
							value : 275
						}, {
							time : '12:50',
							value : 275
						}, {
							time : '13:00',
							value : 423
						}, {
							time : '13:10',
							value : 765
						}, {
							time : '13:20',
							value : 324
						} ];

						this.getComponent('history').getComponent(
								'powerHistory').store.loadData(data);

						var data2 = [ {
							name : 'Stablity',
							value : 1
						}, {
							name : 'Efficiency',
							value : 2
						}, {
							name : 'Capacity',
							value : 3
						}, {
							name : 'Availability',
							value : 4
						} ];
						this.getComponent('evaluation').getComponent('eval').store
								.loadData(data2);
						this.getComponent('banner').html = "Data Center: "
								+ datas.name;
					}
				});