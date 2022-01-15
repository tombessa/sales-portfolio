import React, { useMemo, useEffect, useCallback } from "react";
import {
  useTable,
  useExpanded,
  useFilters,
  usePagination,
  useSortBy,
  useRowSelect,
} from "react-table";
import ReactTooltip from "react-tooltip";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
//@material-ui/core
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import InfoIcon from "@material-ui/icons/Info";
import makeStyles from "@material-ui/core/styles/makeStyles";
//Biblioteca para pesquisas eficientes
import _ from "lodash";
// nodejs library to set properties for components
import PropTypes from "prop-types";
import moment from "moment";

//Custom filters
import {
  fuzzyTextFilterFn,
  DefaultColumnFilter,
  SliderColumnFilter,
  NumberRangeColumnFilter,
  SelectColumnFilter,
  MultiSelectColumnFilter,
  filterGreaterThan,
} from "./Filters";

const useStyles = makeStyles((theme) => ({
  paper: {
    width: "100%",
    overflowX: "auto",
  },
  table: {
    minWidth: 650,
  },
  vermais: {
    fontSize: "11px",
  },
  corVermelho: {
    color: "red",
  },
  corAzul: {
    color: "blue",
  },
  cellNormal: {
  },
  cellCompacto: {
    padding: 2,
    margin: 0,
  },
}));

const IndeterminateCheckbox = React.forwardRef(
  ({ indeterminate, ...rest }, ref) => {
    const defaultRef = React.useRef();
    const resolvedRef = ref || defaultRef;

    React.useEffect(() => {
      resolvedRef.current.indeterminate = indeterminate;
    }, [resolvedRef, indeterminate]);

    return (
      <React.Fragment>
        <input type="checkbox" ref={resolvedRef} {...rest} />
      </React.Fragment>
    );
  }
);

// Our table component
export default function CustomReactTable({ ...props }) {
  const classes = useStyles();
  const {
    onClick,
    linhasPorPagina,
    filtroInicial,
    formatacaoCondicional,
    formatacaoCondicionalEstilo,
    subComponente,
    exibeExpandido,
    setExibeExpandido,
    exibeCheckbox,
    handleCheckbox,
    restringirAcoes,
    semPaginacao,
    layoutCompacto,
    defaultSorted,
  } = props;
  
  const data = useMemo(() => {
    return props.dados ? props.dados : [{}];
  }, [props.dados]);

  const colunas = useMemo(() => {
    return props.colunas ? props.colunas : [{}];
  }, [props.colunas]);

  const onChangeHandleCheckbox = (selecionado, idsSelecionados) => {
    if (handleCheckbox) {
      if (selecionado) {
        selecionado.toggleRowSelected();
        handleCheckbox(selecionado, null);
      } else {
        // Executa sempre, quando seleciona ou em todos
        handleCheckbox(null, idsSelecionados);
      }
    }
  };

  const filterTypes = useMemo(
    () => ({
      // Add a new fuzzyTextFilterFn filter type.
      fuzzyText: fuzzyTextFilterFn,
      // Or, override the default text filter to use
      // "startWith"
      text: (rows, id, filterValue) => {
        return rows.filter((row) => {
          const rowValue = row.values[id];
          return rowValue !== undefined
            ? _.toLower(rowValue).startsWith(_.toLower(filterValue))
            : true;
        });
      },
      multiSelect: (rows, id, filterValue) => {
        if (filterValue.length === 0) {
          return rows;
        }
        return rows.filter((row) => {
          const rowValue = row.values[id];
          return rowValue !== undefined ? filterValue.includes(rowValue) : true;
        });
      },
    }),
    []
  );

  const defaultColumn = useMemo(
    () => ({
      // Let's set up our default Filter UI
      Filter: DefaultColumnFilter,
    }),
    []
  );

  //Aplica o campo de filtro customizado
  const campoFiltro = (tipo) => {
    switch (tipo) {
      case "select":
        return SelectColumnFilter;
      case "multiSelect":
        return MultiSelectColumnFilter;
      case "range":
        return NumberRangeColumnFilter;
      case "slider":
        return SliderColumnFilter;
      default:
        return undefined;
    }
  };

  const geraTableRow = (row) => {
    if (onClick) {
      return (
        <TableRow
          {...row.getRowProps()}
          onClick={() => onClick(row.cells[0].value)}
          hover
          style={{ cursor: "pointer" }}
          key={row.index}
        >
          {row.cells.map((cell, key) => {
            return (
              <TableCell {...cell.getCellProps()} key={key} className={!layoutCompacto ? classes.cellNormal : classes.cellCompacto}>
                {cell.render("Cell")}
              </TableCell>
            );
          })}
        </TableRow>
      );
    }
    if (formatacaoCondicional && formatacaoCondicionalEstilo) {
      return (
        <React.Fragment key={`${Math.random()}-fragmentkey`}>
          <TableRow
            {...row.getRowProps()}
            style={
              formatacaoCondicional(row.original)
                ? { ...formatacaoCondicionalEstilo }
                : {}
            }
            key={row.index}
          >
            {row.cells.map((cell, key) => {
              return (
                <TableCell {...cell.getCellProps()} key={`${key}-cell`} className={!layoutCompacto ? classes.cellNormal : classes.cellCompacto}>
                  {cell.render("Cell")}
                </TableCell>
              );
            })}
          </TableRow>
          {row.isExpanded || exibeExpandido ? (
            <TableRow>
              <TableCell colSpan={allColumns.length} className={!layoutCompacto ? classes.cellNormal : classes.cellCompacto}>
                {subComponente(row.original)}
              </TableCell>
            </TableRow>
          ) : null}
        </React.Fragment>
      );
    }
    return (
      <TableRow {...row.getRowProps()} key={row.index}>
        {row.cells.map((cell, key) => {
          return (
            <TableCell {...cell.getCellProps()} key={`${key}-cell`} className={!layoutCompacto ? classes.cellNormal : classes.cellCompacto}>
              {cell.render("Cell")}
            </TableCell>
          );
        })}
      </TableRow>
    );
  };

  const columns = useMemo(() => {
    const criaColunaDeAcoes = (acoes, props) => {
      // getExpandedToggleProps
      // getToggleRowSelectedProps
      const expandedProps = props.row.getToggleRowExpandedProps();
      return (
        <div>
          {acoes.map((acao) => {
            // console.log(acao);
            // console.log(acao.condicional);
            // console.log(props.row.original[acao.condicional]);
            return (
              (!acao.condicional ||
                (acao.condicional &&
                  !props.row.original[acao.condicional])) && (
                <IconButton
                  aria-label={acao.label}
                  color="primary"
                  size={!layoutCompacto ? "medium" : "small"}
                  onClick={() => acao.onClick(props.row.original)}
                  key={acao.label}
                >
                  {acao.icone}
                </IconButton>
              )
            );
          })}
          {subComponente && (
            <IconButton
              aria-label={expandedProps.title}
              color="primary"
              size={!layoutCompacto ? "medium" : "small"}
              onClick={(event) => {
                if (exibeExpandido) {
                  setExibeExpandido(false);
                } else {
                  expandedProps.onClick(event);
                }
              }}
              key="expanded"
            >
              <InfoIcon />
            </IconButton>
          )}
        </div>
      );
    };

    return colunas.map((coluna) => {
      const base = {
        Header: coluna.cabecalho,
        accessor: coluna.campo,
        width: coluna.largura,
        padding: coluna.padding,
      };

      if (coluna.caracterCondicional1) {
        return {
          ...base,
          disableFilters: coluna.semFiltro ? true : false,
          Cell: (props) => {
            return props.row.values[coluna.campo].substr(
              -coluna.caracterCondicional1.length
            ) === coluna.caracterCondicional1 ||
              props.row.values[coluna.campo].substr(
                -coluna.caracterCondicional2.length
              ) === coluna.caracterCondicional2 ? (
              props.row.values[coluna.campo].substr(
                -coluna.caracterCondicional1.length
              ) === coluna.caracterCondicional1 ? (
                <span
                  data-tip={props.row.original[coluna.tooltip]}
                  className={classes.corVermelho}
                >
                  {props.row.values[coluna.campo]}
                </span>
              ) : (
                <span
                  data-tip={props.row.original[coluna.tooltip]}
                  className={classes.corAzul}
                >
                  {props.row.values[coluna.campo]}
                </span>
              )
            ) : (
              <span>{props.row.values[coluna.campo]}</span>
            );
          },
        };
      }

      if (coluna.acoes) {
        return {
          ...base,
          id: "expander",
          disableFilters: true,
          disableSorting: true,
          Cell: (props) => {
            return criaColunaDeAcoes(coluna.acoes, props);
          },
        };
      }

      if (coluna.campo === "dataCriacao") {
        return {
          id: "dataCriacao",
          disableFilters: true,
          Header: coluna.cabecalho,
          width: coluna.largura,
          sort: "asc",
          accessor: (row) =>
            moment(row.dataCriacao, "DD/MM/YYYY HH:mm:ss").format("x"),
          Cell: (props) => {
            return moment(
              props.row.original.dataCriacao,
              "DD/MM/YYYY HH:mm:ss"
            ).format("DD/MM/YYYY HH:mm:ss");
          },
        };
      }

      if (coluna.campo === "dtAutuacao") {
        return {
          id: "dtAutuacao",
          disableFilters: true,
          Header: coluna.cabecalho,
          width: coluna.largura,
          sort: "asc",
          accessor: (row) => moment(row.dtAutuacao, "DD/MM/YYYY").format("x"),
          Cell: (props) => {
            return moment(props.row.original.dtAutuacao, "DD/MM/YYYY").format(
              "DD/MM/YYYY"
            );
          },
        };
      }

      if (coluna.semFiltro) {
        return {
          ...base,
          disableFilters: true,
        };
      }

      if (coluna.tooltip) {
        return {
          ...base,
          Cell: (props) => {
            return props.row.original[coluna.tooltip] !==
              props.row.values[coluna.campo] ? (
              <span data-tip={props.row.original[coluna.tooltip]}>
                {props.row.values[coluna.campo]}...
              </span>
            ) : (
              <span>{props.row.values[coluna.campo]}</span>
            );
          },
        };
      }

      return {
        ...base,
        Filter: coluna.tipoFiltro
          ? campoFiltro(coluna.tipoFiltro)
          : DefaultColumnFilter,
        filter: coluna.metodoFiltro ? coluna.metodoFiltro : "text",
        sortType: "basic",
      };
    });
  }, [classes.corAzul, classes.corVermelho, colunas, exibeExpandido, layoutCompacto, setExibeExpandido, subComponente]);

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    prepareRow,
    page,
    canPreviousPage,
    canNextPage,
    pageOptions,
    pageCount,
    gotoPage,
    nextPage,
    previousPage,
    setPageSize,
    allColumns,
    rows,
    selectedFlatRows,
    toggleHideColumn,
    toggleRowSelected,
    state: { pageIndex, pageSize, sortBy, selectedRowIds },
  } = useTable(
    {
      columns,
      data,
      defaultColumn,
      filterTypes,
      autoResetPage: true,
      initialState: {
        pageIndex: 0,
        pageSize: linhasPorPagina,
        sortBy: [
          {
            id: defaultSorted?defaultSorted.id:"dataCriacao",
            desc: defaultSorted?defaultSorted.desc:true,
          },
        ],
      },
    },
    useFilters,
    useSortBy,
    useExpanded,
    usePagination,
    useRowSelect,
    (hooks) => {
      if (handleCheckbox) {
        hooks.visibleColumns.push((columns) => [
          {
            id: "selection",
            disableSorting: true,
            // isVisible: false,
            width: 30,
            Header: ({ getToggleAllPageRowsSelectedProps }) => (
              <div>
                Todos
                <IndeterminateCheckbox
                  {...getToggleAllPageRowsSelectedProps({
                    title: "Selecionar todos",
                  })}
                />
              </div>
            ),
            Cell: ({ row }) => (
              <div>
                <IndeterminateCheckbox
                  {...row.getToggleRowSelectedProps({ title: "Selecionar" })}
                  onChange={() => {
                    onChangeHandleCheckbox(row);
                  }}
                />
              </div>
            ),
          },
          ...columns,
        ]);
      }
    }
  );

  useEffect(() => {
    if (handleCheckbox) onChangeHandleCheckbox(null, selectedFlatRows);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedFlatRows]);

  useEffect(() => {
    toggleHideColumn("selection");
  }, [exibeCheckbox, toggleHideColumn]);

  useEffect(() => {
    if (restringirAcoes) toggleHideColumn("expander");
  }, [restringirAcoes, toggleHideColumn]);

  // Força o scroll para o topo da página, durante a paginação ou atualização na tabela
  useEffect(() => {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0;
    ReactTooltip.rebuild();
  }, [pageIndex, pageSize, sortBy]);

  //   useEffect(() => {
  //   console.log(pageIndex);
  //   console.log(pageSize);
  //   console.log(sortBy);
  // }, [pageIndex, pageSize, sortBy]);

  return (
    <div>
      <ReactTooltip place="top" type="info" effect="float" />
      <Table {...getTableProps()} className={classes.table}>
        <TableHead>
          {headerGroups.map((headerGroup) => (
            <TableRow {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map((column, key) => (
                <TableCell
                  component="th"
                  scope="row"
                  {...column.getHeaderProps(
                    column.getSortByToggleProps({ title: "Ordenar" })
                  )}
                  style={{ width: column.width, padding: column.padding }}
                  key={`${key}-header-cell`}
                  className={!layoutCompacto ? classes.cellNormal : classes.cellCompacto}
                >
                  {column.render("Header")}
                  <span style={{ cursor: "pointer" }}>
                    {column.isSorted
                      ? column.isSortedDesc
                        ? " 🔽"
                        : " 🔼"
                      : column.disableSorting
                      ? ""
                      : " 🔽"}
                  </span>
                  <div>{column.canFilter ? column.render("Filter") : null}</div>
                </TableCell>
              ))}
            </TableRow>
          ))}
        </TableHead>
        <TableBody {...getTableBodyProps()}>
          {page.map((row, i) => prepareRow(row) || geraTableRow(row))}
        </TableBody>
      </Table>

      {!semPaginacao && (
        <div className="pagination">
          <br />
          <Button onClick={() => gotoPage(0)} disabled={!canPreviousPage}>
            {"<<"}
          </Button>{" "}
          <Button onClick={() => previousPage()} disabled={!canPreviousPage}>
            {"<"}
          </Button>{" "}
          <Button onClick={() => nextPage()} disabled={!canNextPage}>
            {">"}
          </Button>{" "}
          <Button
            onClick={() => gotoPage(pageCount - 1)}
            disabled={!canNextPage}
          >
            {">>"}
          </Button>{" "}
          <span>
            Página{" "}
            <strong>
              {pageIndex + 1} de {pageOptions.length}
            </strong>{" "}
          </span>
          <span>
            | Ir para página:{" "}
            <input
              type="number"
              defaultValue={pageIndex + 1}
              onChange={(e) => {
                const page = e.target.value ? Number(e.target.value) - 1 : 0;
                gotoPage(page);
              }}
              style={{ width: "100px" }}
            />
          </span>{" "}
          <select
            value={pageSize}
            onChange={(e) => {
              setPageSize(Number(e.target.value));
            }}
          >
            {[5, 10, 20, 30, 40, 50, 100].map((pageSize) => (
              <option key={pageSize} value={pageSize}>
                Exibir {pageSize} linhas por página
              </option>
            ))}
          </select>
          <span> | Total de registros: {rows.length}</span>
        </div>
      )}
    </div>
  );
}

// This is an autoRemove method on the filter function that
// when given the new filter value and returns true, the filter
// will be automatically removed. Normally this is just an undefined
// check, but here, we want to remove the filter if it's not a number
filterGreaterThan.autoRemove = (val) => typeof val !== "number";

// Let the table remove the filter if the string is empty
fuzzyTextFilterFn.autoRemove = (val) => !val;

CustomReactTable.propTypes = {
  titulo: PropTypes.string,
  colunas: PropTypes.array,
  dados: PropTypes.array,
  tipoFiltro: PropTypes.oneOf(["select", "range", "slider"]),
  metodoFiltro: PropTypes.oneOf(["fuzzyText", "equals", "between", "includes"]),
};
