package com.c_felix.pocketmarket.Utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.c_felix.pocketmarket.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Metodos_Estaticos {
    public static final int CODIGO_GALERIA_FOTO = 1;
    public static final int CODIGO_CAMARA_FOTO = 2;
    public static final int CODIGO_GALERIA_VIDEO = 3;
    public static final int CODIGO_CAMARA_VIDEO = 4;

    public static String direccion_Imagen;
    public static Bitmap corregirRotacion(String direccion_Imagen, Bitmap bitmap) {
        Bitmap rotatedBitmap = null;
        try {
            ExifInterface ei = new ExifInterface(direccion_Imagen);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotarImagen(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotarImagen(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotarImagen(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
        } catch (IOException e) {
        }
        return rotatedBitmap;
    }
    public static Bitmap rotarImagen(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public static Bitmap comprimirImagen(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static void eliminarFoto(String ruta) {
        File file = new File(ruta);
        file.delete();
    }
    public static void abrirGaleria(Activity activity, int codigo) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string.seleccione_una_imagen)), codigo);
    }
    public static File crearFoto(Activity activity) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        direccion_Imagen = image.getAbsolutePath();
        return image;
    }

    public static void tomarImagen(Activity activity, Context context, int codigo) {
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamara.resolveActivity(activity.getPackageManager()) != null) {
            try {
                File archivo_foto = crearFoto(activity);
                if (archivo_foto != null) {
                    Uri uri_foto = FileProvider.getUriForFile(context, "com.c_felix.pocketmarket", archivo_foto);
                    intentCamara.putExtra(MediaStore.EXTRA_OUTPUT, uri_foto);
                    activity.startActivityForResult(intentCamara, codigo);
                }
            } catch (IOException ex) {
            }
        }
    }

    public static byte[] imagenArrayBytes(Bitmap imagen, String formato) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (formato.equals("png")) {
            imagen.compress(Bitmap.CompressFormat.PNG, 30, bos);
        } else {
            imagen.compress(Bitmap.CompressFormat.JPEG, 30, bos);
        }
        return bos.toByteArray();
    }

    public static int obtenerValorMaximo(Context contexto, String tabla, String campo) {
        Base_Datos bd = new Base_Datos(contexto);
        SQLiteDatabase db = bd.getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select MAX(" + campo + ") from " + tabla + " ;", null);
            if (c.getCount() == 1) {
                c.moveToFirst();
                int valor = c.getInt(0);
                db.close();
                return valor;
            }
        }
        return 1;
    }
}
