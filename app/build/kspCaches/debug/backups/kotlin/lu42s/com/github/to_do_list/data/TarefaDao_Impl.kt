package lu42s.com.github.to_do_list.`data`

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class TarefaDao_Impl(
  __db: RoomDatabase,
) : TarefaDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTarefa: EntityInsertAdapter<Tarefa>

  private val __deleteAdapterOfTarefa: EntityDeleteOrUpdateAdapter<Tarefa>

  private val __updateAdapterOfTarefa: EntityDeleteOrUpdateAdapter<Tarefa>
  init {
    this.__db = __db
    this.__insertAdapterOfTarefa = object : EntityInsertAdapter<Tarefa>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `tarefas` (`id`,`titulo`,`descricao`,`concluida`,`dataCriacao`,`dataHora`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Tarefa) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.titulo)
        statement.bindText(3, entity.descricao)
        val _tmp: Int = if (entity.concluida) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.dataCriacao)
        val _tmpDataHora: Long? = entity.dataHora
        if (_tmpDataHora == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpDataHora)
        }
      }
    }
    this.__deleteAdapterOfTarefa = object : EntityDeleteOrUpdateAdapter<Tarefa>() {
      protected override fun createQuery(): String = "DELETE FROM `tarefas` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Tarefa) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
    this.__updateAdapterOfTarefa = object : EntityDeleteOrUpdateAdapter<Tarefa>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `tarefas` SET `id` = ?,`titulo` = ?,`descricao` = ?,`concluida` = ?,`dataCriacao` = ?,`dataHora` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Tarefa) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.titulo)
        statement.bindText(3, entity.descricao)
        val _tmp: Int = if (entity.concluida) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.dataCriacao)
        val _tmpDataHora: Long? = entity.dataHora
        if (_tmpDataHora == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpDataHora)
        }
        statement.bindLong(7, entity.id.toLong())
      }
    }
  }

  public override suspend fun inserir(tarefa: Tarefa): Unit = performSuspending(__db, false, true) {
      _connection ->
    __insertAdapterOfTarefa.insert(_connection, tarefa)
  }

  public override suspend fun deletar(tarefa: Tarefa): Unit = performSuspending(__db, false, true) {
      _connection ->
    __deleteAdapterOfTarefa.handle(_connection, tarefa)
  }

  public override suspend fun atualizar(tarefa: Tarefa): Unit = performSuspending(__db, false, true)
      { _connection ->
    __updateAdapterOfTarefa.handle(_connection, tarefa)
  }

  public override fun listarTodas(): Flow<List<Tarefa>> {
    val _sql: String = """
        |
        |        SELECT * FROM tarefas
        |        ORDER BY dataHora IS NULL, dataHora ASC, dataCriacao DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("tarefas")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitulo: Int = getColumnIndexOrThrow(_stmt, "titulo")
        val _columnIndexOfDescricao: Int = getColumnIndexOrThrow(_stmt, "descricao")
        val _columnIndexOfConcluida: Int = getColumnIndexOrThrow(_stmt, "concluida")
        val _columnIndexOfDataCriacao: Int = getColumnIndexOrThrow(_stmt, "dataCriacao")
        val _columnIndexOfDataHora: Int = getColumnIndexOrThrow(_stmt, "dataHora")
        val _result: MutableList<Tarefa> = mutableListOf()
        while (_stmt.step()) {
          val _item: Tarefa
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitulo: String
          _tmpTitulo = _stmt.getText(_columnIndexOfTitulo)
          val _tmpDescricao: String
          _tmpDescricao = _stmt.getText(_columnIndexOfDescricao)
          val _tmpConcluida: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfConcluida).toInt()
          _tmpConcluida = _tmp != 0
          val _tmpDataCriacao: Long
          _tmpDataCriacao = _stmt.getLong(_columnIndexOfDataCriacao)
          val _tmpDataHora: Long?
          if (_stmt.isNull(_columnIndexOfDataHora)) {
            _tmpDataHora = null
          } else {
            _tmpDataHora = _stmt.getLong(_columnIndexOfDataHora)
          }
          _item = Tarefa(_tmpId,_tmpTitulo,_tmpDescricao,_tmpConcluida,_tmpDataCriacao,_tmpDataHora)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
