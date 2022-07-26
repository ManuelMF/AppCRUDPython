package Vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Modelo.Album;
import Modelo.Playlist;


public class Tabla implements MouseListener{
	public JTable tabla;
	public DefaultTableModel modelo;
	public Color colorPar=SystemColor.menu;
	public Color colorImpar=Color.WHITE;
	public Color colorSel=UIManager.getColor("Table.selectionBackground");
	private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();	
	
	
	
	public Tabla(Object[] cabecera, Class<?>[] tipos, Integer[] medidas, Color par, Color impar, Color sel) {
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		leftRenderer.setHorizontalAlignment( SwingConstants.LEFT );
		rightRenderer.setHorizontalAlignment( SwingConstants.RIGHT );
		if (par!=null) colorPar=par;
		if (impar!=null) colorImpar=impar;
		modelo= new DefaultTableModel(null,	cabecera){
			private static final long serialVersionUID = 9209503312321682270L;

			public Class<?> getColumnClass(int columnIdex){
				return tipos[columnIdex];
			}
			public boolean isCellEditable(int row, int column) {
				 if (tipos[column]!=Boolean.class && tipos[column]!=boolean.class) return false;
				 return true;
			    }
		};	
		tabla = new JTable(){
			private static final long serialVersionUID = 1L;

			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		        Component c = super.prepareRenderer(renderer, row, column);
		        if (!isRowSelected(row))c.setBackground(row%2==0? colorPar:colorImpar);
				else c.setBackground(colorSel);
		        return c;
		    }
		};
		tabla.setShowGrid(false);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setModel(modelo);
		
		tabla.setRowHeight(25);
		tabla.setAutoCreateRowSorter(true);

		for (int x=0;x<medidas.length;x++){
			tabla.getColumnModel().getColumn(x).setPreferredWidth(medidas[x]);
			tabla.getColumnModel().getColumn(x).setMinWidth(medidas[x]);
		}

		DefaultTableCellRenderer render=new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 5161847153923664111L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
				if (value instanceof JButton) {
				   return (JButton) value;
				}
				if (value instanceof JComboBox) {
					   return (JComboBox<?>) value;
					}
				return null;
			}
		};
		
		/*tabla.setDefaultEditor(Object.class, null);
		tabla.setDefaultEditor(Double.class, null);
		tabla.setDefaultEditor(Integer.class, null);
		tabla.setDefaultEditor(String.class, null);
		tabla.setDefaultEditor(Fecha.class, null);*/
		
		tabla.setDefaultRenderer(JButton.class, render);
		tabla.setDefaultRenderer(JComboBox.class, render);
		
		tabla.addMouseListener(this);
		
		
	}
	
	public void setResizableColumn(int columIndex, boolean fijo){
		int medida=tabla.getColumnModel().getColumn(columIndex).getPreferredWidth();
		if (fijo) tabla.getColumnModel().getColumn(columIndex).setMaxWidth(10000);
		else tabla.getColumnModel().getColumn(columIndex).setMaxWidth(medida);
		
	}

	public void hideColumn(int i) {
		tabla.removeColumn(tabla.getColumnModel().getColumn(i));
		
	}
	public int getRealSelectedRow() {
		return tabla.getRowSorter().convertRowIndexToModel(tabla.getSelectedRow());
		
	}
	public int getRealSelectedColumn() {
		return tabla.convertColumnIndexToModel(tabla.getSelectedColumn());
		
	}
	public Object getValueSelected(int columnIndex) {
		return getValueAt(tabla.getSelectedRow(), columnIndex);
	}
	
	public Object getValueAt(int fileIndex, int columnIndex) {
		if (fileIndex<0) return null;
		return modelo.getValueAt(tabla.getRowSorter().convertRowIndexToModel(fileIndex), columnIndex); 
	}
	public void vaciar() {
		modelo.setRowCount(0);
	}
	public void alinear(char a, int columIndex) {
		if (Character.toLowerCase(a)=='l') tabla.getColumnModel().getColumn(columIndex).setCellRenderer( leftRenderer );
		if (Character.toLowerCase(a)=='r') tabla.getColumnModel().getColumn(columIndex).setCellRenderer( rightRenderer ); 
		if (Character.toLowerCase(a)=='c') tabla.getColumnModel().getColumn(columIndex).setCellRenderer( centerRenderer ); 
	}
	public void alinearHeader(char a, int columIndex) {
		if (Character.toLowerCase(a)=='l') tabla.getTableHeader().getColumnModel().getColumn(columIndex).setHeaderRenderer(leftRenderer);
		if (Character.toLowerCase(a)=='r') tabla.getTableHeader().getColumnModel().getColumn(columIndex).setHeaderRenderer(rightRenderer);
		if (Character.toLowerCase(a)=='c') tabla.getTableHeader().getColumnModel().getColumn(columIndex).setHeaderRenderer(centerRenderer);
	}

	public void ponerA0() {
		tabla.setRowSelectionInterval(0,0);  
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//if (e.getClickCount()==2 && e.getButton()==1) {
			
			Vista.AppUser.mitabla.tabla.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent ee) {
	            	
	                //if (ee.getClickCount()==1 && ee.getButton()==1) {
	                	
	                    String busqueda = (String) Vista.AppUser.mitabla.getValueSelected(1);
	                    
	                    if (busqueda==null) {
	                    	
	                    	LinkedList<Album> albumLista = Album.load(null, (String) Vista.AppUser.mitabla.getValueSelected(0));
	                    	
	                    	if (albumLista.isEmpty()==false) {
	                    		Album album = albumLista.getFirst();
	                    		// meter un if que mire si IdUser esta null porque no se creo y otro que mire lo mismo desde el menu de cantantes
	                    		//if (Album.ComprobarCantante(album, Controlador.AppUser.IdUser o el de cantantes aun no implementadado)) {new Controlador.MenuMiAlbum(album);}
	                    		//else
	                    		new Controlador.MenuAlbum(album);
	                    		
	                    	}
	
	                    	LinkedList<Playlist> playlistLista =Playlist.load(null, (String) Vista.AppUser.mitabla.getValueSelected(0), null);
	                    	
	                    	if (playlistLista.isEmpty()==false) {
	                    		Playlist playlist = playlistLista.getFirst();
	                    	
	                    		if(Playlist.ComprobarCantante(playlist, Controlador.AppUser.IdUser)==true) {
	                    			new Controlador.MenuMiPlaylist(playlist);
	                    		} else new Controlador.MenuPlaylist(playlist);
	                    		
	                    	}
	                    }
	                	
	               // }
	            }
	        });
			
			Vista.AppUser.mitablaBuscador.tabla.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent eee) {
	                if (eee.getClickCount()==2 && eee.getButton()==1) {
	                	
	                    System.out.println( Vista.AppUser.mitablaBuscador.getValueSelected(0));
	                	
	                }
	            }
	        });
          
           //System.out.println("valor de las tablas: "+ Vista.AppUser.mitabla.getValueSelected(0) + " -- " + Vista.AppUser.mitablaBuscador.getValueSelected(0));
           
       // }
        
        
	}

	
			
			
			
			
          
           //System.out.println("valor de las tablas: "+ Vista.AppUser.mitabla.getValueSelected(0) + " -- " + Vista.AppUser.mitablaBuscador.getValueSelected(0));
           
       // }
        
        
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}